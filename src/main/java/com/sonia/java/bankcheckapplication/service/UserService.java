package com.sonia.java.bankcheckapplication.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonia.java.bankcheckapplication.exceptions.CardCheckExceptions;
import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.bank.category.DischargeEntity;
import com.sonia.java.bankcheckapplication.model.bank.discharge.BankDischarge;
import com.sonia.java.bankcheckapplication.model.bank.factory.BankFactory;
import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.MonoBankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.PrivatBankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.req.balance.BalanceRequestData;
import com.sonia.java.bankcheckapplication.model.bank.req.discharge.DischargeRequestData;
import com.sonia.java.bankcheckapplication.model.bank.resp.CategoryDischargeResponse;
import com.sonia.java.bankcheckapplication.model.user.*;
import com.sonia.java.bankcheckapplication.model.user.req.ChangeUserPasswordRequest;
import com.sonia.java.bankcheckapplication.model.user.req.MonoBankMerchantRequest;
import com.sonia.java.bankcheckapplication.model.user.req.PrivatBankMerchantRequest;
import com.sonia.java.bankcheckapplication.model.user.req.SaveUserRequest;
import com.sonia.java.bankcheckapplication.repository.CategoryRepository;
import com.sonia.java.bankcheckapplication.repository.MerchantRepository;
import com.sonia.java.bankcheckapplication.repository.UserAuthorityRepository;
import com.sonia.java.bankcheckapplication.repository.UserRepository;
import com.sonia.java.bankcheckapplication.util.MerchantDataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserAuthorityRepository authorityRepository;

    private final MerchantRepository merchantRepository;

    private final PasswordEncoder passwordEncoder;

    private final CategoryRepository categoryRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserAuthorityRepository authorityRepository,
                       MerchantRepository merchantRepository,
                       PasswordEncoder passwordEncoder, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.merchantRepository = merchantRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void mergeAdmins(List<SaveUserRequest> requests) {
        if (requests.isEmpty()) return;
        Map<KnownAuthority, CardCheckingUserAuthority> authorities = getAdminAuthorities();
        for (SaveUserRequest request : requests) {
            String email = request.getEmail();
            String nickname = request.getFirstName();
            CardChekingUser user = userRepository.findByEmail(email).orElseGet(() -> {
                var newUser = new CardChekingUser();
                newUser.setCreatedAt(Instant.now());
                newUser.setEmail(email);
                newUser.setFirstName(nickname);
                return newUser;
            });
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.getAuthorities().putAll(authorities);
            System.out.println(user);
            userRepository.save(user);
        }
    }

    private Map<KnownAuthority, CardCheckingUserAuthority> getAdminAuthorities() {
        return authorityRepository.findAllByValueIn(UserAuthorityRepository.ADMIN_AUTHORITIES)
                .collect(Collectors.toMap(
                        CardCheckingUserAuthority::getValue,
                        Function.identity(),
                        (e1, e2) -> e2,
                        () -> new EnumMap<>(KnownAuthority.class)));
    }


    @Transactional
    public UserResponse create(SaveUserRequest request) {
        validateUniqueFields(request);
        return UserResponse.fromUser(save(request, getRegularUserAuthorities()));
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> list(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponse::fromUser);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        CardChekingUser user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User with mail " + email + " not found"));
        Set<KnownAuthority> grantedAuthorities = EnumSet.copyOf(user.getAuthorities().keySet());
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

    private void validateUniqueFields(SaveUserRequest request) {
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw CardCheckExceptions.duplicateEmail(email);
        }
    }

    private CardChekingUser save(SaveUserRequest request, Map<KnownAuthority, CardCheckingUserAuthority> authorities) {
        var user = new CardChekingUser();
        user.getAuthorities().putAll(authorities);
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
        return user;
    }

    private Map<KnownAuthority, CardCheckingUserAuthority> getRegularUserAuthorities() {
        CardCheckingUserAuthority authority = authorityRepository
                .findByValue(KnownAuthority.ROLE_USER)
                .orElseThrow(() -> CardCheckExceptions.authorityNotFound(KnownAuthority.ROLE_USER.name()));

        Map<KnownAuthority, CardCheckingUserAuthority> authorities = new EnumMap<>(KnownAuthority.class);
        authorities.put(KnownAuthority.ROLE_USER, authority);
        return authorities;
    }

    @Transactional
    public UserResponse createAdmin(SaveUserRequest request) {
        validateUniqueFields(request);
        return UserResponse.fromUser(save(request, getAdminAuthorities()));
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserResponse::fromUser);
    }

    @Transactional
    public UserResponse changePasswordByEmail(String email, ChangeUserPasswordRequest request) {
        CardChekingUser user = getUser(email);
        changePassword(user, request.getOldPassword(), request.getNewPassword());
        return UserResponse.fromUser(user);
    }

    private CardChekingUser getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> CardCheckExceptions.userNotFound(email));
    }

    private void changePassword(CardChekingUser user, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw CardCheckExceptions.wrongPassword();
        }
        if (newPassword.equals(oldPassword)) return;
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public CardChekingUser addPrivatBankMerchant(String email, @NotNull PrivatBankMerchantRequest merchantRequest){
        System.out.println(merchantRequest);
        PrivatBankMerchantEntity merchantEntity =
                PrivatBankMerchantEntity.fromPrivatBankMerchantRequest(merchantRequest);
        MerchantDataValidation.validate(merchantEntity);
        return addUserMerchant(email, merchantEntity);
    }

    public CardChekingUser addMonoBankMerchant(String email, @NotNull MonoBankMerchantRequest merchantRequest){
        System.out.println(merchantRequest);
        MonoBankMerchantEntity merchantEntity =
                MonoBankMerchantEntity.fromMonoBankMerchantRequest(merchantRequest);
        MerchantDataValidation.validate(merchantEntity);

        return addUserMerchant(email, merchantEntity);
    }

    @Transactional
    public CardChekingUser addUserMerchant(String email, @NotNull BankMerchantEntity merchantEntity){

        CardChekingUser user = userRepository.findByEmail(email).orElseThrow(() -> CardCheckExceptions.userNotFound(email));
        merchantEntity.setUser(user);
        if(user.getMerchants().contains(merchantEntity)){
            return user;
        }
        user.getMerchants().add(merchantEntity);
        System.out.println(user.getMerchants());
        merchantRepository.save(merchantEntity);
        user = userRepository.save(user);
        return user;
    }

    private List<CategoryDischargeResponse> splitIntoCategories(List<BankDischarge> discharges){

        List<Category> categorySet = categoryRepository.findAll();

        Map<Category, Float> categorySpent = new HashMap<>();

        for (Category category1: categorySet){
            categorySpent.put(category1, (float)0);
        }


        Map<Category, List<BankDischarge>> categoryDischarge = new HashMap<>();
        for(Category category: categorySet){
            categoryDischarge.put(category, new ArrayList<>());
        }

        boolean flag = false;

        for (BankDischarge discharge: discharges){

            for(Category category: categorySet){
                for(DischargeEntity categoryItem: category.getDischarges()) {
                    if (discharge.getDescription() != null && discharge.getDescription().contains(categoryItem.getName())) {
                        categoryDischarge.get(category).add(discharge);
                        categorySpent.put(category, categorySpent.get(category) + discharge.getCardamount());
                        flag = true;
                        break;
                    }
                    if (discharge.getTerminal() != null && discharge.getTerminal().contains(categoryItem.getName())) {
                        categoryDischarge.get(category).add(discharge);
                        categorySpent.put(category, categorySpent.get(category) + discharge.getCardamount());
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    flag = false;
                    break;
                }
            }

        }

        List<CategoryDischargeResponse> responses = new ArrayList<>();

        for (Category key : categoryDischarge.keySet()){
            responses.add(new CategoryDischargeResponse(
                    key.getName(),
                    categoryDischarge.get(key),
                    categorySpent.get(key))
            );
        }
        responses.forEach(System.out::println);

        return responses;


    }



    public List<CategoryDischargeResponse> generateCategorySplitAnswer(@NotBlank String email, int month, int year) {
        CardChekingUser user = getUser(email);
        Set<BankMerchantEntity> bankMerchants = user.getMerchants();

        List<BankDischarge> discharges = new ArrayList<>();

        System.out.println(bankMerchants);

        for (BankMerchantEntity bankMerchant: bankMerchants){
            BankFactory bankFactory= bankMerchant.getBank().getBankFactory();
            DischargeRequestData requestData =
                    bankFactory.getRequestData().setPeriod(month, year).nestMerchant(bankMerchant);
            String data = bankFactory.getDataReceiver().receiveDischarge(requestData);
            discharges.addAll(bankFactory.getParser().parseDischarge(data));
        }
        return this.splitIntoCategories(discharges);

    }

    public float getMerchantTotalBalance(String email) {
        CardChekingUser user = getUser(email);
        Set<BankMerchantEntity> bankMerchants = user.getMerchants();
        float balance = 0;

        for (BankMerchantEntity merchant : bankMerchants) {
            BankFactory monoBankFactory = merchant.getBank().getBankFactory();
            BalanceRequestData balanceRequestData =
                    monoBankFactory.getBalanceRequestData().nestMerchant(merchant);
            String json = monoBankFactory.getDataReceiver().receiveBalance(balanceRequestData);
            balance += monoBankFactory.getParser().parseBalance(json);
        }
        return balance;
    }



    }




