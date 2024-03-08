package com.techriseyou.taskmanager.core;

import com.techriseyou.taskmanager.entity.User;

/**
 * Description: RequestContext.
 * Author: Naveen
 */
public class RequestContext {
    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static Long getCurrentUserId(){
        return currentUserId.get();
    }

    public static void setCurrentUserId(Long userId){
        currentUserId.set(userId);
    }

    public static void clear(){
        currentUserId.remove();
        currentUser.remove();
    }

    public static User getCurrentUser(){
        return currentUser.get();
    }

    public static void setCurrentUser(User user){
        currentUser.set(user);
    }


}
