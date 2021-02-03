package com.sonia.java.bankcheckapplication.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonia.java.bankcheckapplication.exceptions.CardCheckExceptions;
import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.bank.category.DischargeEntity;
import com.sonia.java.bankcheckapplication.model.bank.discharge.BankDischarge;
import com.sonia.java.bankcheckapplication.model.bank.factory.BankFactory;
import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.req.discharge.DischargeRequestData;
import com.sonia.java.bankcheckapplication.model.bank.resp.CategoryDischargeResponse;
import com.sonia.java.bankcheckapplication.model.user.*;
import com.sonia.java.bankcheckapplication.repository.CategoryRepository;
import com.sonia.java.bankcheckapplication.repository.MerchantRepository;
import com.sonia.java.bankcheckapplication.repository.UserAuthorityRepository;
import com.sonia.java.bankcheckapplication.repository.UserRepository;
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
import java.util.*;


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

    @Transactional
    public CardChekingUser addUserMerchant(String email, @NotNull BankMerchantEntity merchantEntity){
        CardChekingUser user = userRepository.findByEmail(email).orElseThrow(() -> CardCheckExceptions.userNotFound(email));
        user.getMerchants().add(merchantEntity);
        merchantEntity.setUser(user);
        merchantRepository.save(merchantEntity);
        user = userRepository.save(user);
        return user;
    }

    private List<CategoryDischargeResponse> splitIntoCategories(List<BankDischarge> discharges){

        List<Category> categorySet = categoryRepository.findAll();

        Map<Category, Float> categorySpent = new HashMap<>();

        for (Category category1: categorySet){
            categorySpent.put(category1, (float)0);
            System.out.println(category1);
            System.out.println(categorySpent.get(category1));
        }


        Map<Category, List<BankDischarge>> categoryDischarge = new HashMap<>();
        for(Category category: categorySet){
            categoryDischarge.put(category, new ArrayList<>());
        }

        Category tempCategory = new Category();
        boolean flag = false;

        for (BankDischarge discharge: discharges){

            for(Category category: categorySet){
                for(DischargeEntity categoryItem: category.getDischarges()) {
                    if (discharge.getDescription() != null && discharge.getDescription().contains(categoryItem.getName())) {
                        tempCategory.setName(category.getName());
                        categoryDischarge.get(tempCategory).add(discharge);
                        categorySpent.put(tempCategory, categorySpent.get(tempCategory) + discharge.getCardamount());
                        flag = true;
                        break;
                    }
                    if (discharge.getTerminal() != null && discharge.getTerminal().contains(categoryItem.getName())) {
                        tempCategory.setName(category.getName());
                        categoryDischarge.get(tempCategory).add(discharge);
                        categorySpent.put(tempCategory, categorySpent.get(tempCategory) + discharge.getCardamount());
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
                    key,
                    categoryDischarge.get(key),
                    categorySpent.get(key))
            );
        }
        //responses.forEach(System.out::println);

        return responses;


    }


    public void generateCategorySplitAnswer(@NotBlank String email, int month, int year) throws IOException {
        CardChekingUser user = getUser(email);
        Set<BankMerchantEntity> bankMerchants = user.getMerchants();

        List<BankDischarge> discharges = new ArrayList<>();


        for (BankMerchantEntity bankMerchant: bankMerchants){
            BankFactory bankFactory= bankMerchant.getBank().getBankFactory();
            bankFactory.getRequestData().setPeriod(month, year).nestMerchant(bankMerchant);
            DischargeRequestData requestData = bankFactory.getRequestData();
            String data = bankFactory.getDataReceiver().receiveDischarge(requestData);
            discharges.addAll(bankFactory.getParser().parseDischarge(data));
        }

        List<CategoryDischargeResponse> responses = this.splitIntoCategories(discharges);
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, responses);
        String json = out.toString();



    }


}




