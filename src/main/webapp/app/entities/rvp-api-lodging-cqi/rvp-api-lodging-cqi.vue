<template>
  <div>
    <h2 id="page-heading" data-cy="RvpApiLodgingCqiHeading">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.home.title')" id="rvp-api-lodging-cqi-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RvpApiLodgingCqiCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rvp-api-lodging-cqi"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rvpApiLodgingCqis && rvpApiLodgingCqis.length === 0">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="rvpApiLodgingCqis && rvpApiLodgingCqis.length > 0">
      <table class="table table-striped" aria-describedby="rvpApiLodgingCqis">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.lodgingId')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.averageCurrentPeriod')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.tendancy')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.change')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.name')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.averagePreviousPeriod')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.fd')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.td')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rvpApiLodgingCqi in rvpApiLodgingCqis" :key="rvpApiLodgingCqi.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RvpApiLodgingCqiView', params: { rvpApiLodgingCqiId: rvpApiLodgingCqi.id } }">{{
                rvpApiLodgingCqi.id
              }}</router-link>
            </td>
            <td>{{ rvpApiLodgingCqi.lodgingId }}</td>
            <td>{{ rvpApiLodgingCqi.averageCurrentPeriod }}</td>
            <td>{{ rvpApiLodgingCqi.tendancy }}</td>
            <td>{{ rvpApiLodgingCqi.change }}</td>
            <td>{{ rvpApiLodgingCqi.name }}</td>
            <td>{{ rvpApiLodgingCqi.averagePreviousPeriod }}</td>
            <td>{{ rvpApiLodgingCqi.fd }}</td>
            <td>{{ rvpApiLodgingCqi.td }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RvpApiLodgingCqiView', params: { rvpApiLodgingCqiId: rvpApiLodgingCqi.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RvpApiLodgingCqiEdit', params: { rvpApiLodgingCqiId: rvpApiLodgingCqi.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rvpApiLodgingCqi)"
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
          id="reviewproHandlerApplicationApp.rvpApiLodgingCqi.delete.question"
          data-cy="rvpApiLodgingCqiDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-rvpApiLodgingCqi-heading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rvpApiLodgingCqi"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeRvpApiLodgingCqi()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./rvp-api-lodging-cqi.component.ts"></script>
