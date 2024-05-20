<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="reviewproHandlerApplicationApp.rvpApiResponse.home.createOrEditLabel"
          data-cy="RvpApiResponseCreateUpdateHeading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="rvpApiResponse.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="rvpApiResponse.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.surveyId')"
              for="rvp-api-response-surveyId"
            ></label>
            <input
              type="number"
              class="form-control"
              name="surveyId"
              id="rvp-api-response-surveyId"
              data-cy="surveyId"
              :class="{ valid: !v$.surveyId.$invalid, invalid: v$.surveyId.$invalid }"
              v-model.number="v$.surveyId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.lodgingId')"
              for="rvp-api-response-lodgingId"
            ></label>
            <input
              type="number"
              class="form-control"
              name="lodgingId"
              id="rvp-api-response-lodgingId"
              data-cy="lodgingId"
              :class="{ valid: !v$.lodgingId.$invalid, invalid: v$.lodgingId.$invalid }"
              v-model.number="v$.lodgingId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.date')"
              for="rvp-api-response-date"
            ></label>
            <div class="d-flex">
              <input
                id="rvp-api-response-date"
                data-cy="date"
                type="datetime-local"
                class="form-control"
                name="date"
                :class="{ valid: !v$.date.$invalid, invalid: v$.date.$invalid }"
                :value="convertDateTimeFromServer(v$.date.$model)"
                @change="updateInstantField('date', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.overallsatsifaction')"
              for="rvp-api-response-overallsatsifaction"
            ></label>
            <input
              type="number"
              class="form-control"
              name="overallsatsifaction"
              id="rvp-api-response-overallsatsifaction"
              data-cy="overallsatsifaction"
              :class="{ valid: !v$.overallsatsifaction.$invalid, invalid: v$.overallsatsifaction.$invalid }"
              v-model.number="v$.overallsatsifaction.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.customScore')"
              for="rvp-api-response-customScore"
            ></label>
            <input
              type="number"
              class="form-control"
              name="customScore"
              id="rvp-api-response-customScore"
              data-cy="customScore"
              :class="{ valid: !v$.customScore.$invalid, invalid: v$.customScore.$invalid }"
              v-model.number="v$.customScore.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiResponse.plantorevisit')"
              for="rvp-api-response-plantorevisit"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="plantorevisit"
              id="rvp-api-response-plantorevisit"
              data-cy="plantorevisit"
              :class="{ valid: !v$.plantorevisit.$invalid, invalid: v$.plantorevisit.$invalid }"
              v-model="v$.plantorevisit.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./rvp-api-response-update.component.ts"></script>
