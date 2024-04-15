package com.wiley.DuoFinance.security;

import com.wiley.DuoFinance.exception.CannotLoginException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class Session {

  // Map<UserHashId, UserID>
//  private final static List<String> users = new ArrayList<>();
  private final static String SESSION_COOKIE_NAME = "userIdHash";


  /**
   * add User to the session and return a friendly UserType you can send to the client
   * @param userHash the hashed user id
   * returns a Cookie
   *
   * @see Cookie
   * */
  public static Cookie add(String userHash) throws Exception {
    // Add the user to the session

    // Create a cookie with the userHash
    Cookie cookie = new Cookie(SESSION_COOKIE_NAME, userHash);
    cookie.setMaxAge(7200); // 2 hours

    return cookie;
  }


  public static Cookie remove(){
    Cookie cookie = new Cookie(SESSION_COOKIE_NAME, "");
    cookie.setMaxAge(0);
    return cookie;
  }
//
//  /**
//   * Check if the user exists in the session
//   * @param userIdHash the hashed user id store in the cookie
//   * @return the user id if the user exists in the session else null
//   *
//   * */
//  private static String isExists(String userIdHash) {
//    return users.contains(userIdHash) ? userIdHash : null;
//  }


  public static String getHash(HttpServletRequest request) throws CannotLoginException {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(SESSION_COOKIE_NAME)) {
          return cookie.getValue();
        }
      }
    }

    throw new CannotLoginException();
  }
}
