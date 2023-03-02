package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.category.api.ICategoryServicePort;
import com.pragma.powerup.domain.category.spi.ICategoryPersistencePort;
import com.pragma.powerup.domain.category.usecase.CategoryUseCase;
import com.pragma.powerup.domain.employee.api.IEmployeeServicePort;
import com.pragma.powerup.domain.employee.api.IUserEmployeeServicePort;
import com.pragma.powerup.domain.employee.spi.IEmployeePersistencePort;
import com.pragma.powerup.domain.employee.spi.IUserEmployeePersistencePort;
import com.pragma.powerup.domain.employee.usecase.EmployeeUseCase;
import com.pragma.powerup.domain.employee.usecase.UserEmployeeUseCase;
import com.pragma.powerup.domain.order.api.IOrderServicePort;
import com.pragma.powerup.domain.order.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.order.usecase.OrderUseCase;
import com.pragma.powerup.domain.orderplates.api.IOrderPlatesServicePort;
import com.pragma.powerup.domain.orderplates.spi.IOrderPlatesPersistencePort;
import com.pragma.powerup.domain.orderplates.usecase.OrderPlatesUseCase;
import com.pragma.powerup.domain.plate.api.IPlateServicePort;
import com.pragma.powerup.domain.plate.spi.IPlatePersistencePort;
import com.pragma.powerup.domain.plate.usecase.PlateUseCase;
import com.pragma.powerup.domain.restaurant.api.IRestaurantServicePort;
import com.pragma.powerup.domain.restaurant.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.restaurant.usecase.RestaurantUseCase;
import com.pragma.powerup.infrastructure.category.out.jpa.adapter.CategoryJpaAdapter;
import com.pragma.powerup.infrastructure.category.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup.infrastructure.category.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.client.IMessagingClient;
import com.pragma.powerup.infrastructure.client.IUserClient;
import com.pragma.powerup.infrastructure.client.model.UserDto;
import com.pragma.powerup.infrastructure.client.model.messaging.IMessageMapper;
import com.pragma.powerup.infrastructure.employee.out.jpa.adapter.EmployeeJpaAdapter;
import com.pragma.powerup.infrastructure.employee.out.jpa.adapter.UserEmployeeJpaAdapter;
import com.pragma.powerup.infrastructure.employee.out.jpa.mapper.IEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.employee.out.jpa.mapper.IUserEmployeeClientMapper;
import com.pragma.powerup.infrastructure.employee.out.jpa.repository.IEmployeeRepository;
import com.pragma.powerup.infrastructure.order.out.jpa.adapter.OrderJpaAdapter;
import com.pragma.powerup.infrastructure.order.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.order.out.jpa.repository.IOrderRepository;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.adapter.OrderPlateJpaAdapter;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.mapper.IOrderPlateEntityMapper;
import com.pragma.powerup.infrastructure.orderplate.out.jpa.repository.IOrderPlateRepository;
import com.pragma.powerup.infrastructure.plate.out.jpa.adapter.PlateJpaAdapter;
import com.pragma.powerup.infrastructure.plate.out.jpa.mapper.IPlateEntityMapper;
import com.pragma.powerup.infrastructure.plate.out.jpa.repository.IPlateRepository;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.restaurant.out.jpa.repository.IRestaurantRepository;
import com.pragma.powerup.infrastructure.security.aut.DetailsUser;
import com.pragma.powerup.infrastructure.security.aut.IUserDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IMessagingClient messagingClient;
    private final IMessageMapper messageMapper;

    private final HttpServletRequest request;

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderPlateRepository orderPlateRepository;
    private final IOrderPlateEntityMapper orderPlateEntityMapper;
    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;
    private final IUserEmployeeClientMapper userEmployeeClientMapper;

    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper plateEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IUserDetailsMapper userDetailsMapper;
    private final IUserClient userClient;

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(messageMapper, messagingClient, request, userClient, orderRepository, orderEntityMapper,
                restaurantRepository, orderPlateRepository, employeeRepository, orderPlateEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort());
    }

    @Bean
    public IOrderPlatesPersistencePort orderPlatesPersistencePort() {
        return new OrderPlateJpaAdapter(orderPlateRepository, orderPlateEntityMapper);
    }

    @Bean
    public IOrderPlatesServicePort orderPlatesServicePort() {
        return new OrderPlatesUseCase(orderPlatesPersistencePort());
    }

    @Bean
    public IEmployeePersistencePort employeePersistencePort() {
        return new EmployeeJpaAdapter(employeeRepository, employeeEntityMapper);
    }

    @Bean
    public IEmployeeServicePort employeeServicePort() {
        return new EmployeeUseCase(employeePersistencePort());
    }

    @Bean
    public IUserEmployeePersistencePort userEmployeePersistencePort() {
        return new UserEmployeeJpaAdapter(request, userClient, userEmployeeClientMapper, restaurantRepository, employeeRepository);
    }

    @Bean
    public IUserEmployeeServicePort userEmployeeServicePort() {
        return new UserEmployeeUseCase(userEmployeePersistencePort());
    }

    @Bean
    public IPlatePersistencePort platePersistencePort() {
        return new PlateJpaAdapter(request, userClient, restaurantRepository, plateRepository, plateEntityMapper, categoryRepository);
    }

    @Bean
    public IPlateServicePort plateServicePort() {
        return new PlateUseCase(platePersistencePort());
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(request, userClient, restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {

        return username -> optionalDetailsUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private Optional<DetailsUser> optionalDetailsUser(String username) {
        UserDto userDto = userClient.getUserAuth(username).getBody();

        DetailsUser user = userDetailsMapper.toUserDetail(userDto);
        return Optional.of(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}