import type { LinksFunction } from "@remix-run/node";
import { Link } from "@remix-run/react";
import style from "~/styles/login.css?url";

export const links: LinksFunction = () => [{ rel: "stylesheet", href: style }];

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
              <div className="flex">
                <Link className="eurostyle link-to" to={"/signin"}>
                  create an account!
                </Link>
              </div>
            </form>
          </div>
        </div>
      </section>
    </>
  );
};
export default LogIn;
