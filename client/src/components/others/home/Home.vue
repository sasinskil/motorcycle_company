<template>
  <div class="home">
    <div class="statistics">
        <ul class="statistics__list">
          <statistic-card v-for="(statistic, idx) in statistics" :key="idx"
            :title="`${statistic.title}`"
            :count="statistic.counter"
            :moveTo="`${statistic.moveto}`"
            :iconName="`${statistic.iconName}`"
          >
          </statistic-card>
      </ul>
    </div>
  </div>
</template>
<script>
import { countCustomers, countEmployees, countMotorcycleDetails, countTestDrives,
     countTransactions, countServices } from '@/variables';
import statisticCard from "@/components/others/home/statisticCard.vue";

export default {
  name: 'Home',
  components: {
    'statistic-card' : statisticCard,
  },
  data() {
    return {
      statistics: [
        {
          title: 'KLIENCI',
          counter: 0,
          moveto: 'customers',
          iconName: 'users',
        },
        {
          title: 'PRACOWNICY',
          counter: 0,
          moveto: 'employees',
          iconName: 'user-tag',
        },
        {
          title: 'MOTOCYKLE',
          counter: 0,
          moveto: 'motorcycleDetails',
          iconName: 'motorcycle',
        },
        {
          title: 'JAZDY TESTOWE',
          counter: 0,
          moveto: 'testDrives',
          iconName: 'road',
        },
        {
          title: 'TRANSAKCJE',
          counter: 0,
          moveto: 'transactions',
          iconName: 'handshake',
        },
        {
          title: 'SERWISY',
          counter: 0,
          moveto: 'services',
          iconName: 'wrench',
        }
      ],
    };
  },
  methods: {
    getCountOfCustomers() {
      this.$http
        .get(`${countCustomers}`)
        .then(response => response.json())
        .then(count => {
          this.statistics[0].counter = count;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getCountOfEmployees() {
      this.$http
        .get(`${countEmployees}`)
        .then(response => response.json())
        .then(count => {
          this.statistics[1].counter = count;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getCountOfMotorcycleDetails() {
      this.$http
        .get(`${countMotorcycleDetails}`)
        .then(response => response.json())
        .then(count => {
          this.statistics[2].counter = count;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getCountOfTestDrives() {
      this.$http
        .get(`${countTestDrives}`)
        .then(response => response.json())
        .then(count => {
          this.statistics[3].counter = count;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getCountOfTransactions() {
      this.$http
        .get(`${countTransactions}`)
        .then(response => response.json())
        .then(count => {
          this.statistics[4].counter = count;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getCountOfServices() {
      this.$http
        .get(`${countServices}`)
        .then(response => response.json())
        .then(count => {
          this.statistics[5].counter = count;
        })
        .catch(err => {
          console.log(err);
        });
    },
  },
  created() {
    this.getCountOfCustomers();
    this.getCountOfEmployees();
    this.getCountOfMotorcycleDetails();
    this.getCountOfTestDrives();
    this.getCountOfTransactions();
    this.getCountOfServices();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

.home {
  width: 100%;
  margin: 0 auto;
}

.statistics__list {
  margin: 0;
  padding: 0;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  list-style-type: none;
}

</style>
