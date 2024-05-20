<template>
  <div>
    <h2 id="page-heading" data-cy="RvpApilodgingHeading">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.home.title')" id="rvp-apilodging-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RvpApilodgingCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rvp-apilodging"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rvpApilodgings && rvpApilodgings.length === 0">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="rvpApilodgings && rvpApilodgings.length > 0">
      <table class="table table-striped" aria-describedby="rvpApilodgings">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.ida')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.name')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rvpApilodging in rvpApilodgings" :key="rvpApilodging.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RvpApilodgingView', params: { rvpApilodgingId: rvpApilodging.id } }">{{
                rvpApilodging.id
              }}</router-link>
            </td>
            <td>{{ rvpApilodging.ida }}</td>
            <td>{{ rvpApilodging.name }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RvpApilodgingView', params: { rvpApilodgingId: rvpApilodging.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RvpApilodgingEdit', params: { rvpApilodgingId: rvpApilodging.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rvpApilodging)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="reviewproHandlerApplicationApp.rvpApilodging.delete.question"
          data-cy="rvpApilodgingDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-rvpApilodging-heading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rvpApilodging"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeRvpApilodging()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./rvp-apilodging.component.ts"></script>
