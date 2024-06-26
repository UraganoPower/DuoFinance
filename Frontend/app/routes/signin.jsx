import { Form, Link, useFetcher, useNavigate } from "@remix-run/react";
import ArrowRSvg from "~/svg/arrowRSvg";
import style from "~/styles/login.css";
import { useEffect, useRef, useState } from "react";
import { StatusCodes } from "http-status-codes";

export const links = () => [{ rel: "stylesheet", href: style }];

const SignIn = () => {
  const userNameRef = useRef();
  const emailRef = useRef();
  const navigate = useNavigate();
  const passwordRef = useRef();
  const confirmpPasswordRef = useRef();
  const [data, setData] = useState();

  useEffect(() => {
    fetch("http://localhost:8080/api/login", {
      method: "Get",
      credentials: "include",
    }).then((res) => {
      if (res.status === StatusCodes.OK) {
        navigate("/game");
      }
    });
  }, []);

  const handleFetchSignIn = () => {
    if (passwordRef.current.value !== confirmpPasswordRef.current.value) {
      setData({ errors: { password: "passwords don't match" } });
      return;
    }

    fetch("http://localhost:8080/api/user", {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: userNameRef.current.value,
        email: emailRef.current.value,
        password: passwordRef.current.value,
      }),
    }).then((res) => {
      console.log(res);
      if (res.status === StatusCodes.CREATED) {
        navigate("/game");
      } else {
        res.json().then((data) => setData(data));
      }
    });
  };

  return (
    <>
      <section className="flex">
        <div className="w-[50%] h-[100vh] land-img"></div>
        <div className="w-[50%] h-[100vh] ">
          <div className="wrapper-log">
            <h1 className="europa title-log">DUO FINANCE</h1>
            <h3 className="europa title-log-msg">Sign in!</h3>
            <div className="mt-[40px]">
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">Username</span>
                <input
                  ref={userNameRef}
                  className="input-log europa"
                  name="username"
                  type="text"
                  required
                />
                {data?.errors?.username ? (
                  <p className="input-error">{data.errors.username}</p>
                ) : null}
              </div>
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">Email</span>
                <input
                  ref={emailRef}
                  className="input-log europa"
                  name="email"
                  type="text"
                  required
                />
                {data?.errors?.email ? (
                  <p className="input-error">{data.errors.email}</p>
                ) : null}
              </div>
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">Password</span>
                <input
                  ref={passwordRef}
                  className="input-log europa"
                  name="password"
                  type="text"
                  required
                />
                {data?.errors?.password ? (
                  <p className="input-error">{data.errors.password}</p>
                ) : null}
              </div>
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">
                  Confirm password
                </span>
                <input
                  ref={confirmpPasswordRef}
                  className="input-log europa"
                  name="password"
                  type="text"
                  required
                />
              </div>
              <div className="flex justify-between w-[530px]">
                <Link className="eurostyle" to={"/login"}>
                  <span className="link-to-login">
                    Back to login{" "}
                    <ArrowRSvg className={"ml-[15px]"}></ArrowRSvg>
                  </span>
                </Link>
                <button className="log-button" onClick={handleFetchSignIn}>
                  <span className="eurostyle">create</span>
                </button>
              </div>
            </div>
          </div>
        </div>
        <div className="github europa">
          <span>(v0.0.1)</span>
          <Link
            className="github-link"
            to={"https://github.com/UraganoPower/DuoFinance"}
          >
            https://github.com/UraganoPower/DuoFinance
          </Link>
        </div>
      </section>
    </>
  );
};
export default SignIn;
