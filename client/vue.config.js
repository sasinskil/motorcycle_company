module.exports = {
  devServer: {
    port: 8080
  },
    css: {
      loaderOptions: {
        sass: {
          prependData: `@import "@/assets/styles/main.scss";`
        },
      }
    }
  }