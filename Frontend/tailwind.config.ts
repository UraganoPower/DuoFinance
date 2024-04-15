import type { Config } from "tailwindcss";
import { nextui } from "@nextui-org/theme";

export default {
  content: [
    "./app/**/*.{js,jsx,ts,tsx}",
    "./node_modules/@nextui-org/theme/dist/components/button.js",
    "./node_modules/@nextui-org/theme/dist/components/(button|snippet|code|input).js",
  ],
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
  darkMode: "class",
  plugins: [nextui()],
} satisfies Config;
