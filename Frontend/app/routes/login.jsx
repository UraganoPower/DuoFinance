import { Link } from "@remix-run/react";
import style from "~/styles/login.css";
import ArrowSvg from "~/svg/arrowSvg";

export const links = () => [{ rel: "stylesheet", href: style }];

const LogIn = () => {
  return (
    <>
      <section className="flex">
        <div className="w-[50%] h-[100vh] land-img"></div>
        <div className="w-[50%] h-[100vh] ">
          <div className="wrapper-log">
            <h1 className="europa title-log">DUO FINANCE</h1>
            <h3 className="europa title-log-msg">WelcomeBack!</h3>
            <form className="mt-[88px]">
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">Email</span>
                <input className="input-log europa" name="email" type="text" />
              </div>
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">Password</span>
                <input
                  className="input-log europa"
                  name="password"
                  type="password"
                />
              </div>
              <div className="flex justify-between w-[530px]">
                <Link className="eurostyle link-to" to={"/signin"}>
                  create an account!
                </Link>
                <button className="log-button" disabled>
                  <span className="eurostyle">Login</span>
                  <ArrowSvg className={"ml-[15px]"}></ArrowSvg>
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
export default LogIn;
