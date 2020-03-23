<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <button @click="$emit('confirm')" class="burger-hide">
          <span class="burger-hide--box">
            <span class="burger-hide--inner"></span>
          </span>
        </button>
        <div class="modal-container">
          <div class="modal-header">
            <h1 class="modal-header__title">{{headerText}}</h1>
          </div>

          <div class="modal-body">
            <p class="modal-body--text">{{bodyText}}</p>
          </div>

          <div class="modal-footer">
            <button class="modal-default-button--action" @click="$emit('confirm')">Ok</button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: "InfoModal",
  props: {
    headerText: {
      type: String,
      required: true
    },
    bodyText: {
      type: String,
      required: true
    }
  },
  data() {
    return {};
  },
  methods: {
    confirm() {
      this.$emit("confirm");
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.modal-mask {
  position: fixed;
  z-index: 9999;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  transition: opacity 0.3s ease-in-out;
}

.modal-wrapper {
  position: relative;
  padding: 1rem;
  width: 100%;
  max-width: 440px;
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  width: 100%;
  margin: 0px auto;
  padding: 15px 15px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: $default-shadow;
  transition: all 0.3s ease;
}

.modal-header {
  margin-top: 0;

  &__title {
    margin: 2rem 0 3rem 0;
    padding-bottom: 0.5rem;
    position: relative;
    text-align: center;

    &::after {
      position: absolute;
      display: block;
      content: "";
      bottom: 0;
      left: 0;
      right: 0;
      margin: 0 auto;
      width: 65%;
      height: 0.05rem;
      background-color: $navy-blue;
    }
  }
}

.modal-body {
  margin: 0 0 4.5rem 0;
  &--text {
    font-size: 1.1rem;
    max-height: 160px;
    overflow-y: auto;
    text-align: center;
    padding: 0 2rem;
  }
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.modal-default-button--action {
  @include default-button($navy-blue);
}

.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.3s;
}
.modal-enter, .modal-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}

@mixin hamburger-line {
  width: 100%;
  height: 2px;
  background-color: $white;
  position: absolute;
}

.burger-hide {
  position: absolute;
  top: 8px;
  right: 8px;
  box-shadow: $default-shadow;
  border-radius: 50%;
  cursor: pointer;
  padding: 0.5rem 0.5rem;
  border: none;
  background-color: $navy-blue;
  transition: transform 0.2s ease-in-out, background-color 0.3s ease-in-out;

  &:hover {
    background-color: $bright-red;
  }

  &--box {
    width: 15px;
    height: 10px;
    display: inline-block;
    position: relative;
  }

  &--inner {
    @include hamburger-line;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    background-color: transparent;

    &::before,
    &::after {
      @include hamburger-line;
      left: 0;
      content: "";
    }

    &::before {
      top: -10px;
      transform: translateY(10px) rotate(45deg);
    }

    &::after {
      top: 10px;
      transform: translateY(-10px) rotate(-45deg);
    }
  }
}
</style>
