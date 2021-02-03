package com.sonia.java.bankcheckapplication;

import com.sonia.java.bankcheckapplication.model.bank.merchant.PrivatBankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.user.CardChekingUser;
import com.sonia.java.bankcheckapplication.repository.CategoryRepository;
import com.sonia.java.bankcheckapplication.repository.MerchantRepository;
import com.sonia.java.bankcheckapplication.repository.UserAuthorityRepository;
import com.sonia.java.bankcheckapplication.repository.UserRepository;
import com.sonia.java.bankcheckapplication.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Before
    public void setUp(){
        userRepository = mock(UserRepository.class);
        authorityRepository = mock(UserAuthorityRepository.class);
        merchantRepository = mock(MerchantRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        categoryRepository = mock(CategoryRepository.class);
        userService = new UserService(userRepository,
                authorityRepository,
                merchantRepository,
                passwordEncoder, categoryRepository);
    }

    @Test
    public void testAddUserMerchant(){
        String merchantId = "abc";
        String merchantSignature = "abcde";
        String cardNumber = "123456";
        String email = "user@gmail.com";
        PrivatBankMerchantEntity privatBankMerchantEntity = new PrivatBankMerchantEntity();
        privatBankMerchantEntity.setMerchantId(merchantId);
        privatBankMerchantEntity.setMerchantSignature(merchantSignature);
        privatBankMerchantEntity.setCardNumber(cardNumber);
        CardChekingUser user = new CardChekingUser();
        user.setId((long)123);
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(merchantRepository.save(same(privatBankMerchantEntity))).thenReturn(privatBankMerchantEntity);
        when(userRepository.save(same(user))).thenReturn(user);

        CardChekingUser userResponse = userService.addUserMerchant(email, privatBankMerchantEntity);
        assertThat(userResponse).isEqualTo(user);
        assertThat(userResponse.getMerchants().contains(privatBankMerchantEntity)).isEqualTo(true);
        assertThat(userResponse.getMerchants().toArray()[0] instanceof PrivatBankMerchantEntity).isEqualTo(true);

        verify(userRepository).findByEmail(email);
        verify(userRepository).save(user);
        verify(merchantRepository).save(privatBankMerchantEntity);
    }

}
