<template>
  <div id="singleMotDetails" class="main-container--motDetails single-motDetails">
    <button class="single-motDetails__button" @click="backToList">
      <font-awesome-icon
        class="arrow-left-icon icon-arrow"
        icon="arrow-left"
      />Powrót
    </button>
    <div class="single-motDetails--box">
      <h1 class="single-motDetails__title">
        Dane szczegółowe
      </h1>
      <p class="single-motDetails__paragraph">
        <strong>Kod motocykla:</strong> {{ motorcycleDetails.motorcycleCode }}
      </p>
      <p class="single-motDetails__paragraph">
        <strong>Cena (PLN):</strong> {{ motorcycleDetails.price }}
      </p>
      <p class="single-motDetails__paragraph">
        <strong>Model:</strong> {{ motorcycleDetails.motorcycle.model }}
      </p>
      <p class="single-motDetails__paragraph">
        <strong>Marka:</strong> {{ motorcycleDetails.motorcycle.brand }}
      </p>
      <p class="single-motDetails__paragraph">
        <strong>Klasyfikacja:</strong> {{ motorcycleDetails.motorcycle.classification }}
      </p>
      <p class="single-motDetails__paragraph">
        <strong>Moc (km):</strong> {{ motorcycleDetails.motorcycle.power }}
      </p>
      <p class="single-motDetails__paragraph">
        <strong>Rodzaj silnika:</strong> {{ motorcycleDetails.motorcycle.engineType }}
      </p>
      <p class="single-motDetails__paragraph">
        <strong>Pojemność (cm <span class="cubic-centimeters">3</span> ):</strong> {{ motorcycleDetails.motorcycle.capacity}}
      </p>
      <p class="single-motDetails__paragraph">
        <strong>Waga (kg):</strong> {{ motorcycleDetails.motorcycle.weight}}
      </p>
    </div>
    <div class="buttons-wrapper">
      <button
        title="Edytuj"
        v-if="checkIsAdmin"
        class="my-button edit"
        @click="edit"
      >
        Edytuj
        <font-awesome-icon class="icon" icon="eraser" />
      </button>
      <button
        title="Usuń"
        v-if="checkIsAdmin"
        class="my-button delete"
        @click="deleteMotDetails"
      >
        Usuń
        <font-awesome-icon class="icon" icon="trash-alt" />
      </button>
    </div>
    <InfoModal
      v-if="showInfoModal"
      :headerText="modalHeaderContent"
      :bodyText="modalBodyContent"
      @confirm="close"
    />
    <ActionModal
      v-if="showActionModal"
      :headerText="modalHeaderContent"
      :bodyText="modalBodyContent"
      @action="doAction"
      @cancel="close"
    />
  </div>
</template>

<script>
import { motorcycleDetailsUrl } from "@/variables";
import InfoModal from "@/components/modal/InfoModal";
import ActionModal from "@/components/modal/ActionModal";
export default {
  name: "singleMotDetails",
  components: {
      InfoModal,
      ActionModal
  },
  data() {
    return {
      motorcycleDetailsId: this.$route.params.id,
      motorcycleDetails: {
          id: null,
          motorcycleCode: '',
          price: '',
          motorcycle: {
                id: null,
                model: '',
                brand: '',
                classification: '',
                engineType: '',
                capacity: '',
                power: '',
                weight:'',
          },
      },
      modalHeaderContent: "",
      modalBodyContent: "",
      showInfoModal: false,
      showActionModal: false,
    };
  },
  methods: {
     close() {
      this.showInfoModal = false;
      this.showActionModal = false;
    },
    edit() {
      this.$router.replace(`/motorcycleDetails/${this.motorcycleDetails.id}/edit`);
    },
    doAction() {
               this.$http
          .delete(`${motorcycleDetailsUrl}/${this.motorcycleDetailsId}`)
          .then(() => {
            this.showActionModal = false;

            setTimeout(() => {
                this.$router.push("/motorcycleDetails");
            }, 300);
          })
          .catch(() => {
            this.showActionModal = false;
            this.modalHeaderContent = "Uwaga!";
            this.modalBodyContent = "Motocykl nie może zostać usunięty, upewnij się, czy nie został zarezerowany dla jednej z usług salonu!";
            this.showInfoModal = true;
          });
    },
    deleteMotDetails() {
        this.modalHeaderContent = "Uwaga!";
        this.modalBodyContent = `Czy na pewno chcesz usunąć motocykl ?`;
        this.showActionModal = true;
    },
    backToList() {
      this.$router.go(-1);
    },
    getMotorcycleDetails() {
      this.$http
        .get(`${motorcycleDetailsUrl}/${this.motorcycleDetailsId}`)
        .then(response => response.json())
        .then(object => {
          this.motorcycleDetails = object;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  computed: {
    checkIsAdmin() {
      return this.$store.getters.isAdmin;
    }
  },
  created() {
      this.getMotorcycleDetails();
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/styles/main.scss";
@keyframes moveYe {
  0% {
    transform: translateX(0px);
  }
  50% {
    transform: translateX(-3px);
  }
  100% {
    transform: translateX(0px);
  }
}

.cubic-centimeters {
    position: absolute;
    top: -5px;
    left: 118px;
    font-size: 0.7rem;
}

.single-motDetails {
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
  text-align: left;
  &__title--address {
      margin-top: 2.5rem;
  }

  &__paragraph {
    margin: 2rem 0 0;
    position: relative;
    &--info {
      text-align: right;
    }
  }
  &__strong {
    margin: 2rem 0;
    font-size: 1.1rem;
  }
  &--box {
    padding: 1rem;
    box-shadow: 0px 6px 16px rgba(24, 41, 67, 0.2);
    border-radius: 0.2rem;
    background: #fff;
  }
  &__button {
    @include default-button($navy-blue);
    margin: 0 0 1.5rem 0;
    &:hover > svg {
      animation: moveYe 0.8s infinite;
    }
  }
}
.icon {
  width: 15px;
  height: 15px;
  margin-left: 0.5rem;
}
.icon-arrow {
  width: 15px;
  height: 15px;
  margin-right: 0.5rem;
  animation: moveYe 0.8s;
}
.my-button {
  margin-top: 0.5rem;
}
.my-button + .my-button {
  margin-left: 0.5rem;
}
.edit {
  @include default-button($dark-blue);
}
.delete {
  @include default-button($dark-red);
}
</style>
