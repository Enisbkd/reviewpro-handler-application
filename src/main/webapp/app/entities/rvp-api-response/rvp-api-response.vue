<template>
  <div>
    <h2 id="page-heading" data-cy="RvpApiResponseHeading">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.home.title')" id="rvp-api-response-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'RvpApiResponseCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-rvp-api-response"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && rvpApiResponses && rvpApiResponses.length === 0">
      <span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="rvpApiResponses && rvpApiResponses.length > 0">
      <table class="table table-striped" aria-describedby="rvpApiResponses">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.surveyId')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.lodgingId')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.date')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.overallsatsifaction')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.customScore')"></span></th>
            <th scope="row"><span v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.plantorevisit')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rvpApiResponse in rvpApiResponses" :key="rvpApiResponse.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RvpApiResponseView', params: { rvpApiResponseId: rvpApiResponse.id } }">{{
                rvpApiResponse.id
              }}</router-link>
            </td>
            <td>{{ rvpApiResponse.surveyId }}</td>
            <td>{{ rvpApiResponse.lodgingId }}</td>
            <td>{{ formatDateShort(rvpApiResponse.date) || '' }}</td>
            <td>{{ rvpApiResponse.overallsatsifaction }}</td>
            <td>{{ rvpApiResponse.customScore }}</td>
            <td>{{ rvpApiResponse.plantorevisit }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'RvpApiResponseView', params: { rvpApiResponseId: rvpApiResponse.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'RvpApiResponseEdit', params: { rvpApiResponseId: rvpApiResponse.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(rvpApiResponse)"
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
          id="reviewproHandlerApplicationApp.rvpApiResponse.delete.question"
          data-cy="rvpApiResponseDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p
          id="jhi-delete-rvpApiResponse-heading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.delete.question', { id: removeId })"
        ></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-rvpApiResponse"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeRvpApiResponse()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./rvp-api-response.component.ts"></script>
