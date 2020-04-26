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
        <strong>Pojemność (cm <span class="cubic-centimeters--preview">3</span> ):</strong> {{ motorcycleDetails.motorcycle.capacity}}
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
            this.modalHeaderContent = "Uwaga";
            this.modalBodyContent = "Motocykl nie może zostać usunięty, upewnij się, czy nie został zarezerowany dla jednej z usług salonu!";
            this.showInfoModal = true;
          });
    },
    deleteMotDetails() {
        this.modalHeaderContent = "Uwaga";
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
</style>
