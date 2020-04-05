module.exports = {
  devServer: {
    port: 8080,
    host: '0.0.0.0',
    public: "192.168.1.12:8080/",
    hot: true,
    disableHostCheck: true,
    proxy: "http://localhost:9090/api"
  },
    css: {
      loaderOptions: {
        sass: {
          prependData: `@import "@/assets/styles/main.scss";`
        },
      }
    }
  }