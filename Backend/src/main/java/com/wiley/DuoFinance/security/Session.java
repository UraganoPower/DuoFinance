package com.wiley.DuoFinance.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class Session {

  // Map<UserHashId, UserID>
  private final static Map<String, String> users = new HashMap<>();
  private final static String SESSION_COOKIE_NAME = "userIdHash";


  /**
   * add User to the session and return a friendly UserType you can send to the client
   * @param userHash the hashed user id
   * @param userID the id of the user
   * returns a Cookie
   *
   * @see Cookie
   * */
  public static Cookie add(String userHash, String userID) throws Exception {
    // Add the user to the session
    users.put(userHash, userID);

    // Create a cookie with the userHash
    Cookie cookie = new Cookie(SESSION_COOKIE_NAME, userHash);
    cookie.setMaxAge(7200); // 2 hours

    return cookie;
  }

  /**
   * Check if the user exists in the session
   * @param userIdHash the hashed user id store in the cookie
   * @return the user id if the user exists in the session else null
   *
   * */
  public static String isExists(String userIdHash) {
    return users.get(userIdHash);
  }


  public static Cookie findCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(SESSION_COOKIE_NAME)) {
          return cookie;
        }
      }
    }
    return null;
  }
}
