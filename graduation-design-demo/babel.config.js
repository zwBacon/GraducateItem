module.exports = {
    plugins: [
        ["@nutui/babel-plugin-separate-import", {
            "libraryName": "@nutui/nutui",
            "libraryDirectory": "dist/packages",
            "style": "scss"
        }]
    ],
   presets: [
    '@vue/cli-plugin-babel/preset'
  ]
}

