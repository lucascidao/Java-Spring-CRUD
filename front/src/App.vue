
<template>
  <div>
    <!-- Header -->
    <div class="row  bg-dark px-2 mb-4 py-2 ">

      <div class="col-6 d-flex align-items-center "><span class="font-weight-bold fs-2" style="color: white;">User
          Table</span>
      </div>
      <div class="col-6 d-flex  align-items-center flex-row-reverse "><b-button variant="primary"
          @click="infoStore($event.target)">Add user</b-button>
      </div>

    </div>
    <b-container fluid>
      <!-- Pagination -->
      <div class="d-flex  align-items-center flex-row-reverse">
        <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage"
          aria-controls="my-table"></b-pagination>
      </div>
      <!-- Table -->
      <b-table id="my-table" striped hover :items="items" :fields="fields" bordered :per-page="perPage"
        :current-page="currentPage" responsive>
        <template #cell(actions)="row">

          <b-button size="sm" class="mx-2 mb-2 mb-sm-0" variant="info" @click="info(row.item, $event.target)">
            Edit
          </b-button>

          <b-button size="sm" class="mx-2" variant="danger" @click="infoDelete(row.item, $event.target)">
            Delete
          </b-button>
        </template></b-table>
    </b-container>

    <!-- update and create model -->
    <b-container fluid>
      <b-modal :id="infoModal.id" header-bg-variant="info" header-text-variant="light" :title="infoModal.title"
        @hide="resetInfoModal" ref="update-modal" hide-footer>
        <template #modal-header="{ close }">
          <h5>{{infoModal.title}}</h5>
          <b-button size="sm" variant="danger" @click="close()">
            Close
          </b-button>
         
        </template>
        <b-form @submit.prevent="onSubmit">
          <b-form-group id="input-group-1" label="Name:" label-for="input-1">
            <b-form-input id="input-1" v-model="form.name" placeholder="Enter name" required></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-2" label="Age:" label-for="input-2">
            <b-form-input id="input-2" v-model="form.age" placeholder="Enter age"></b-form-input>
          </b-form-group>
          <b-form-group id="input-group-3" label="Email:" label-for="input-3">
            <b-form-input id="input-3" v-model="form.email" placeholder="Enter e-mail" type="email"
              required></b-form-input>
          </b-form-group>
          <b-button class="mt-4" type="submit" variant="primary">Submit</b-button>
        </b-form>
      </b-modal>
    </b-container>

    <!-- delete modal -->
    <b-modal :id="deleteModal.id" header-bg-variant="danger" header-text-variant="light" :title="deleteModal.title"
      @hide="resetDeleteModal" ref="delete-modal" hide-footer>
      <template #modal-header="{ close }">
          <h5>{{deleteModal.title}}</h5>
          <b-button size="sm" variant="secondary" @click="close()">
            Close
          </b-button>
         
        </template>
      <b-form @submit.prevent="onDelete">
        <div class="mb-4">Confirm to delete the user.</div>
          <b-button size="sm" type="submit" variant="danger" class="mx-auto">Delete</b-button>
      </b-form>
    </b-modal>

  </div>
</template>

<script>
export default {
  data() {
    return {
      perPage: 5,
      currentPage: 1,
      fields: ['id', 'name', 'age', 'email', 'actions'],
      items: [],
      form: {
        name: '',
        age: '',
        email: '',
      },
      id: null,
      infoModal: {
        id: 'info-modal',
        title: '',
      },
      deleteModal: {
        id: 'delete-modal',
        title: '',
      }
    }
  },
  computed: {
    rows() {
      return this.items.length
    }
  },
  mounted() {
    this.fetchDataFromAPI();
  },
  methods: {
    //api
    /* eslint-disable */
    fetchDataFromAPI() {
      this.$axios
        .get('http://localhost:8081/users')
        .then(response => {
          this.items = response.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    onSubmit() {
      if (this.id != null) {
        this.$axios
          .put('http://localhost:8081/users/' + this.id, this.form)
          .then(response => {
            this.resetInfoModal();
            this.$refs['update-modal'].hide()
            this.fetchDataFromAPI();
            this.resetInfoModal();

          })
          .catch(error => {
            console.log(error);
          });
      }
      else {
        this.$axios
          .post('http://localhost:8081/users', this.form)
          .then(response => {
            this.resetInfoModal();
            this.$refs['update-modal'].hide()
            this.fetchDataFromAPI();
            this.resetInfoModal();

          })
          .catch(error => {
            console.log(error);
          });
      }

    },
    onDelete() {
      this.$axios
        .delete('http://localhost:8081/users/' + this.id)
        .then(response => {
          this.fetchDataFromAPI();
          this.$refs['delete-modal'].hide()
          this.resetDeleteModal();
          this.resetInfoModal();

        })
        .catch(error => {
          console.log(error);
        });
    },
    // -----
    info(item, button) {
      this.infoModal.title = 'Id:' + item.id + ' | Name:' + item.name;
      this.id = item.id;
      this.form.name = item.name;
      this.form.age = item.age;
      this.form.email = item.email;
      this.$root.$emit('bv::show::modal', this.infoModal.id, button)
    },
    infoStore(button) {
      this.infoModal.title = "Add an user";
      this.$root.$emit('bv::show::modal', this.infoModal.id, button)
    },
    infoDelete(item, button) {
      this.deleteModal.title = 'Id:' + item.id + ' | Name:' + item.name;
      this.id = item.id;
      this.$root.$emit('bv::show::modal', this.deleteModal.id, button)
    },
    resetInfoModal() {
      this.infoModal.title = ''
      this.id = null;
      this.form.name = '';
      this.form.age = '';
      this.form.email = "";

    },
    resetDeleteModal() {
      this.deleteModal.title = '';
    },

  },


}
</script>
