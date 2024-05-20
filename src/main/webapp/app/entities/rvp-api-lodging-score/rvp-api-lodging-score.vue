<template>
  <div>
    <h2 id="page-heading" data-cy="RvpApiLodgingScoreHeading">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.home.title')" id="rvp-api-lodging-score-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RvpApiLodgingScoreCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rvp-api-lodging-score"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rvpApiLodgingScores && rvpApiLodgingScores.length === 0">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="rvpApiLodgingScores && rvpApiLodgingScores.length > 0">
      <table class="table table-striped" aria-describedby="rvpApiLodgingScores">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.lodgingId')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.surveyId')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.nps')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.rating')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.customScore')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.fd')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.td')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rvpApiLodgingScore in rvpApiLodgingScores" :key="rvpApiLodgingScore.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RvpApiLodgingScoreView', params: { rvpApiLodgingScoreId: rvpApiLodgingScore.id } }">{{
                rvpApiLodgingScore.id
              }}</router-link>
            </td>
            <td>{{ rvpApiLodgingScore.lodgingId }}</td>
            <td>{{ rvpApiLodgingScore.surveyId }}</td>
            <td>{{ rvpApiLodgingScore.nps }}</td>
            <td>{{ rvpApiLodgingScore.rating }}</td>
            <td>{{ rvpApiLodgingScore.customScore }}</td>
            <td>{{ rvpApiLodgingScore.fd }}</td>
            <td>{{ rvpApiLodgingScore.td }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RvpApiLodgingScoreView', params: { rvpApiLodgingScoreId: rvpApiLodgingScore.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RvpApiLodgingScoreEdit', params: { rvpApiLodgingScoreId: rvpApiLodgingScore.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rvpApiLodgingScore)"
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
          id="reviewproHandlerApplicationApp.rvpApiLodgingScore.delete.question"
          data-cy="rvpApiLodgingScoreDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-rvpApiLodgingScore-heading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rvpApiLodgingScore"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeRvpApiLodgingScore()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./rvp-api-lodging-score.component.ts"></script>
