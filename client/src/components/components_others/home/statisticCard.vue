<template>
  <li class="statistics__item card">
    <div class="card__body">
      <div class="card__main-content">
        <h2 class="card__title">{{title}}</h2>
        <span :class="[{danger: count === 0}, 'card__count']">{{count | checkIsEmpty}}</span>
      </div>
      <p class="card__icon-wrapper card__icon-wrapper--user">
        <font-awesome-icon class="card__icon" :icon="`${iconName}`" />
      </p>
    </div>
    <div class="card__footer">
      <router-link class="card__footer--link" :to="`/${moveTo}`"
        >Przenieś do listy
        <font-awesome-icon
          class="card__footer--icon icon"
          icon="chevron-circle-right"
        />
      </router-link>
    </div>
  </li>
</template>

<script>
export default {
  name: "statisticCard",
  props: {
      title: String,
      count: Number,
      moveTo: String,
      iconName: String,
  },
  data() {
    return {};
  },
  filters: {
    checkIsEmpty(value) {
      if (value === 0) {
        return `BRAK`;
      } else {
        return value;
      }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.home {
  width: 100%;
  margin: 0 auto;
}

.card {
  width: 100%;
  margin: 0 2rem 3rem;
  max-width: 420px;
  box-shadow: $default-shadow;
  padding: 1rem 2rem 1.5rem;
  text-align: left;
  transition: box-shadow 0.3s ease-in-out;
  @media only screen and (max-width: 1040px) {
      margin: 0 1rem 2rem;
      max-width: 370px;
    }

  &:hover {
    box-shadow: $default-shadow-grow;
  }

  &__body {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  &__title {
    margin: 0 0 1rem 0;
    font-size: 1.1rem;

    @media only screen and (max-width: 450px) {
        display: none;
    }
  }

  &__count {
    font-size: 1.7rem;
    @media only screen and (max-width: 450px) {
        font-size: 2.5rem;
    }
    font-weight: bold;
    color: $orange;
    &.danger {
      color: $dark-red;
      font-weight: bold;
      font-size: 1.3rem;
    }
  }

  &__icon-wrapper {
    padding: 1rem 1.2rem;
    margin: 0.5rem 0 0;
    background-color: $lazur;
    border-radius: 50%;
    color: $white;
    font-size: 2rem;
    @media only screen and (max-width: 450px) {
        padding: 0.5rem 0.7rem;
        font-size: 1.8rem;
    }

    svg {
      margin: 0;
    }
  }

  &__footer {
    margin-top: 2rem;
    font-weight: bold;
    font-size: .9rem;

    &--link {
      text-decoration: none;
      color: $navy-blue;
      position: relative;
      padding: 0.5rem 2.5rem 0.5rem 0;
      transition: color 0.4s ease-in-out;

      &:hover {
          color: $bright-green;
      }

      &:hover > .card__footer--icon {
          transform: translateX(2px);
      }
    }

    &--icon {
      width: 17px;
      height: 17px;
      margin-left: 1rem;
      color: $bright-green;
      font-size: 1.3rem;
      position: absolute;
      top: 10px;
      right: 4px;
      transition: transform 0.4s ease-in-out;
    }
  }
}
</style>
