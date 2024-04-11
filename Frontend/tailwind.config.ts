import type { Config } from "tailwindcss";

export default {
  content: ["./app/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        text: "#00030C",
        background: "#fbfbfe",
        primary: "#165CFF",
        secondary: "#FF6F8D",
        accent: "#FF9437",
      },
      backgroundImage: {
        land: "url('/public/image/landScapeLog.png')",
      },
      fontFamily: {
        europa: ["Europa", "sans-serif"],
        eurostyle: ["Eurostyle", "sans-serif"],
      },
    },
  },
  plugins: [],
} satisfies Config;
