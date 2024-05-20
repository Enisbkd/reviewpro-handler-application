<template>
  <div>
    <h2 id="page-heading" data-cy="RvpapilodgingHeading">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpapilodging.home.title')" id="rvpapilodging-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('reviewproHandlerApplicationApp.rvpapilodging.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RvpapilodgingCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rvpapilodging"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpapilodging.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rvpapilodgings && rvpapilodgings.length === 0">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpapilodging.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="rvpapilodgings && rvpapilodgings.length > 0">
      <table class="table table-striped" aria-describedby="rvpapilodgings">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpapilodging.ida')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpapilodging.name')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rvpapilodging in rvpapilodgings" :key="rvpapilodging.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RvpapilodgingView', params: { rvpapilodgingId: rvpapilodging.id } }">{{
                rvpapilodging.id
              }}</router-link>
            </td>
            <td>{{ rvpapilodging.ida }}</td>
            <td>{{ rvpapilodging.name }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RvpapilodgingView', params: { rvpapilodgingId: rvpapilodging.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RvpapilodgingEdit', params: { rvpapilodgingId: rvpapilodging.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rvpapilodging)"
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
          id="reviewproHandlerApplicationApp.rvpapilodging.delete.question"
          data-cy="rvpapilodgingDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-rvpapilodging-heading"
          v-text="t$('reviewproHandlerApplicationApp.rvpapilodging.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rvpapilodging"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeRvpapilodging()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./rvpapilodging.component.ts"></script>
