package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUsernameAndPassword(String username, String password);
}
