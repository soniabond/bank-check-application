package com.sonia.java.bankcheckapplication;

import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.bank.category.UserCategoryLimit;
import com.sonia.java.bankcheckapplication.model.bank.merchant.PrivatBankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.resp.UserCategoryLimitResponse;
import com.sonia.java.bankcheckapplication.model.user.CardCheckingUser;
import com.sonia.java.bankcheckapplication.model.user.req.PrivatBankMerchantRequest;
import com.sonia.java.bankcheckapplication.repository.*;
import com.sonia.java.bankcheckapplication.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Array;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)

public class UserServiceIntegrationTest {

    UserRepository userRepository;

    UserAuthorityRepository authorityRepository;

    MerchantRepository merchantRepository;

    UserService userService;

    PasswordEncoder passwordEncoder;

    CategoryRepository categoryRepository;

    UserCategoryLimitRepository limitRepository;

    @Before
    public void setUp(){
        userRepository = mock(UserRepository.class);
        authorityRepository = mock(UserAuthorityRepository.class);
        merchantRepository = mock(MerchantRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        categoryRepository = mock(CategoryRepository.class);
        limitRepository = mock(UserCategoryLimitRepository.class);
        userService = new UserService(userRepository,
                authorityRepository,
                merchantRepository,
                passwordEncoder, categoryRepository, limitRepository);
    }

    /*public void testAddUserMerchant(){
        String merchantId = "abc";
        String merchantSignature = "abcde";
        String cardNumber = "123456";
        String email = "user@gmail.com";
        PrivatBankMerchantEntity privatBankMerchantEntity = new PrivatBankMerchantEntity();
        privatBankMerchantEntity.setMerchantId(merchantId);
        privatBankMerchantEntity.setMerchantSignature(merchantSignature);
        privatBankMerchantEntity.setCardNumber(cardNumber);
        CardCheckingUser user = new CardCheckingUser();
        user.setId((long)123);
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(merchantRepository.save(same(privatBankMerchantEntity))).thenReturn(privatBankMerchantEntity);
        when(userRepository.save(same(user))).thenReturn(user);

        CardCheckingUser userResponse = userService.addUserMerchant(email, privatBankMerchantEntity);
        assertThat(userResponse).isEqualTo(user);
        assertThat(userResponse.getMerchants().contains(privatBankMerchantEntity)).isEqualTo(true);
        assertThat(userResponse.getMerchants().toArray()[0] instanceof PrivatBankMerchantEntity).isEqualTo(true);
        PrivatBankMerchantEntity merchantEntity = (PrivatBankMerchantEntity) userResponse.getMerchants().toArray()[0];
        assertThat(merchantEntity.getMerchantId().equals(merchantId)).isEqualTo(true);
        assertThat(merchantEntity.getMerchantSignature().equals(merchantSignature)).isEqualTo(true);

        verify(userRepository).findByEmail(email);
        verify(userRepository).save(user);
        verify(merchantRepository).save(privatBankMerchantEntity);
    }


    public void fromPrivatBankMerchant(){
        String merchantId = "abc";
        String merchantSignature = "abcde";
        String cardNumber = "123456";
        String email = "user@gmail.com";

        PrivatBankMerchantRequest merchantRequest = new PrivatBankMerchantRequest();
        merchantRequest.setMerchantId(merchantId);
        merchantRequest.setMerchantSignature(merchantSignature);
        merchantRequest.setCardNumber(cardNumber);


        PrivatBankMerchantEntity merchantEntity =
                PrivatBankMerchantEntity.fromPrivatBankMerchantRequest(merchantRequest);
        CardCheckingUser user = new CardCheckingUser();
        user.setId((long)123);
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(merchantRepository.save(same(merchantEntity))).thenReturn(merchantEntity);
        when(userRepository.save(same(user))).thenReturn(user);

        CardCheckingUser userResponse = userService.addPrivatBankMerchant(email, merchantRequest);
        assertThat(userResponse).isEqualTo(user);
        assertThat(userResponse.getMerchants().contains(merchantEntity)).isEqualTo(true);
        assertThat(userResponse.getMerchants().toArray()[0] instanceof PrivatBankMerchantEntity).isEqualTo(true);
        PrivatBankMerchantEntity merchantEntity1 = (PrivatBankMerchantEntity) userResponse.getMerchants().toArray()[0];
        assertThat(merchantEntity1.getMerchantId().equals(merchantId)).isEqualTo(true);
        assertThat(merchantEntity1.getMerchantSignature().equals(merchantSignature)).isEqualTo(true);

        verify(userRepository).findByEmail(email);
        verify(userRepository).save(user);
//        verify(merchantRepository).save(merchantEntity);
    }*/

    @Test
    public void setCategoryLimitTest(){
        String email = "user@gmail.com";
        CardCheckingUser user = new CardCheckingUser();
        user.setId((long)123);
        user.setEmail(email);

        String name = "taxi";
        Category category = new Category();
        category.setName(name);
        category.setId(1);

        String name1 = "flowers";
        Category category1 = new Category();
        category.setName(name1);
        category.setId(2);

        int newLimit = 500;


        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(categoryRepository.findByName(name)).thenReturn(Optional.of(category));
        when(userRepository.save(same(user))).thenReturn(user);
        CardCheckingUser userResponse = userService.setCategoryLimit(email, name, 100);

        assertThat(userResponse.getLimits().size()).isEqualTo(1);

        CardCheckingUser userResponse1 = userService.setCategoryLimit(email, name, newLimit);
        assertThat(userResponse1.getLimits().size()).isEqualTo(1);

        UserCategoryLimit userCategoryLimit = (UserCategoryLimit) userResponse1.getLimits().toArray()[0];
        assertThat(userCategoryLimit.getLimit()).isEqualTo(newLimit);

    }

    @Test
    public void getCategoryLimitAfterSavingNew(){
        String email = "user@gmail.com";
        CardCheckingUser user = new CardCheckingUser();
        user.setId((long)123);
        user.setEmail(email);

        String name = "taxi";
        Category category = new Category();
        category.setName(name);
        category.setId(1);
        int newLimit = 500;

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(categoryRepository.findByName(name)).thenReturn(Optional.of(category));
        when(userRepository.save(same(user))).thenReturn(user);
        CardCheckingUser userResponse = userService.setCategoryLimit(email, name, 500);

        assertThat(userResponse.getLimits().size()).isEqualTo(1);

        UserCategoryLimitResponse categoryLimitResponse = userService.getCategoryLimit(email, name);
        assertThat(categoryLimitResponse.getLimit() == 500).isEqualTo(true);
        assertThat(categoryLimitResponse.getCategoryName().equals(name)).isEqualTo(true);
        verify(categoryRepository, times(2)).findByName(name);



    }




}
