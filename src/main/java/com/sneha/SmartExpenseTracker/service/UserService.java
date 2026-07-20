package com.sneha.SmartExpenseTracker.service;

import com.sneha.SmartExpenseTracker.entity.User;

public interface UserService {

    User registerUser(User user);

    User getUserByEmail(String email);

}