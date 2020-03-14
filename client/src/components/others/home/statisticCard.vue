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
  max-width: 440px;
  box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
  padding: 1rem 2rem 1.5rem;
  text-align: left;
  transition: box-shadow 0.3s ease-in-out;
  @media only screen and (max-width: 1040px) {
      margin: 0 1rem 2rem;
      max-width: 370px;
    }

  &:hover {
    box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.5);
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
    color: #FF8310;
    &.danger {
      color: #FF4D4D;
      font-weight: bold;
      font-size: 1.3rem;
    }
  }

  &__icon-wrapper {
    padding: 1rem 1.2rem;
    margin: 0.5rem 0 0;
    background-color: #427C8F;
    border-radius: 50%;
    color: #fff;
    font-size: 2rem;

    &--user {
      padding: 1rem 1.2rem;
    }
  }

  &__footer {
    margin-top: 2rem;
    font-weight: bold;
    font-size: .9rem;

    &--link {
      text-decoration: none;
      color: #3a5066;
      position: relative;
      padding: 0.5rem 2.5rem 0.5rem 0;
      transition: color 0.4s ease-in-out;

      &:hover {
          color: #86BBA3;
      }

      &:hover > .card__footer--icon {
          transform: translateX(2px);
      }
    }

    &--icon {
      margin-left: 1rem;
      color: #86BBA3;
      font-size: 1.2rem;
      position: absolute;
      top: 9px;
      right: 4px;
      transition: transform 0.4s ease-in-out;
    }
  }
}
</style>
