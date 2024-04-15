import { Link, useNavigate } from "@remix-run/react";
import { StatusCodes } from "http-status-codes";
import { useEffect, useRef, useState } from "react";
import style from "~/styles/login.css";
import ArrowSvg from "~/svg/arrowSvg";

export const links = () => [{ rel: "stylesheet", href: style }];

const LogIn = () => {
  const emailRef = useRef();
  const passwordRef = useRef();
  const navigate = useNavigate();
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

  const handleFetchLogin = () => {
    fetch("http://localhost:8080/api/login", {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: emailRef.current.value,
        password: passwordRef.current.value,
      }),
    }).then((res) => {
      if (res.status === StatusCodes.OK) {
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
            <h3 className="europa title-log-msg">WelcomeBack!</h3>
            <div className="mt-[88px]">
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">Email</span>
                <input
                  className="input-log europa"
                  name="email"
                  type="text"
                  ref={emailRef}
                />
              </div>
              <div className="input-log-wrapper">
                <span className="input-log-label eurostyle">Password</span>
                <input
                  className="input-log europa"
                  name="password"
                  type="password"
                  ref={passwordRef}
                />
                {data?.errors?.login ? (
                  <p className="input-error">{data.errors.login}</p>
                ) : null}
              </div>
              <div className="flex justify-between w-[530px] mt-[30px]">
                <Link className="eurostyle link-to" to={"/signin"}>
                  create an account!
                </Link>
                <button className="log-button" onClick={handleFetchLogin}>
                  <span className="eurostyle">Login</span>
                  <ArrowSvg className={"ml-[15px]"}></ArrowSvg>
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
export default LogIn;
