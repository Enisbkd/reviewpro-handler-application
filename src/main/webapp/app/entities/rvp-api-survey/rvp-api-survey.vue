<template>
  <div>
    <h2 id="page-heading" data-cy="RvpApiSurveyHeading">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.home.title')" id="rvp-api-survey-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RvpApiSurveyCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rvp-api-survey"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rvpApiSurveys && rvpApiSurveys.length === 0">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="rvpApiSurveys && rvpApiSurveys.length > 0">
      <table class="table table-striped" aria-describedby="rvpApiSurveys">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.overallScoreEnabled')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.languages')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.outOf')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.name')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.actibe')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.pids')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.primary')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rvpApiSurvey in rvpApiSurveys" :key="rvpApiSurvey.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RvpApiSurveyView', params: { rvpApiSurveyId: rvpApiSurvey.id } }">{{
                rvpApiSurvey.id
              }}</router-link>
            </td>
            <td>{{ rvpApiSurvey.overallScoreEnabled }}</td>
            <td>{{ rvpApiSurvey.languages }}</td>
            <td>{{ rvpApiSurvey.outOf }}</td>
            <td>{{ rvpApiSurvey.name }}</td>
            <td>{{ rvpApiSurvey.actibe }}</td>
            <td>{{ rvpApiSurvey.pids }}</td>
            <td>{{ rvpApiSurvey.primary }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RvpApiSurveyView', params: { rvpApiSurveyId: rvpApiSurvey.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RvpApiSurveyEdit', params: { rvpApiSurveyId: rvpApiSurvey.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rvpApiSurvey)"
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
          id="reviewproHandlerApplicationApp.rvpApiSurvey.delete.question"
          data-cy="rvpApiSurveyDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-rvpApiSurvey-heading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rvpApiSurvey"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeRvpApiSurvey()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./rvp-api-survey.component.ts"></script>
