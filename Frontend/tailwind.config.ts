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
    },
  },
  plugins: [],
} satisfies Config;
