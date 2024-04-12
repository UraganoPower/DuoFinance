import { Link } from "@remix-run/react";
import ArrowRSvg from "~/svg/arrowRSvg";
import style from "~/styles/login.css";

export const links = () => [{ rel: "stylesheet", href: style }];

const SignIn = () => {
  return (
    <>
      <section className="flex">
        <div className="w-[50%] h-[100vh] land-img"></div>
        <div className="w-[50%] h-[100vh] ">
          <div className="wrapper-log">
            <h1 className="europa title-log">DUO FINANCE</h1>
            <h3 className="europa title-log-msg">Sign in!</h3>
            <form className="mt-[40px]">
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">Username</span>
                <input
                  className="input-log europa"
                  name="username"
                  type="text"
                />
              </div>
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">Email</span>
                <input className="input-log europa" name="email" type="text" />
              </div>
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">Password</span>
                <input
                  className="input-log europa"
                  name="password"
                  type="text"
                />
              </div>
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">
                  Confirm password
                </span>
                <input
                  className="input-log europa"
                  name="password"
                  type="text"
                />
              </div>
              <div className="flex justify-between w-[530px]">
                <Link className="eurostyle" to={"/login"}>
                  <span className="link-to-login">
                    Back to login{" "}
                    <ArrowRSvg className={"ml-[15px]"}></ArrowRSvg>
                  </span>
                </Link>
                <button className="log-button" disabled>
                  <span className="eurostyle">create</span>
                </button>
              </div>
            </form>
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
