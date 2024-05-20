<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="reviewproHandlerApplicationApp.rvpApiLodgingScore.home.createOrEditLabel"
          data-cy="RvpApiLodgingScoreCreateUpdateHeading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="rvpApiLodgingScore.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="rvpApiLodgingScore.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.lodgingId')"
              for="rvp-api-lodging-score-lodgingId"
            ></label>
            <input
              type="number"
              class="form-control"
              name="lodgingId"
              id="rvp-api-lodging-score-lodgingId"
              data-cy="lodgingId"
              :class="{ valid: !v$.lodgingId.$invalid, invalid: v$.lodgingId.$invalid }"
              v-model.number="v$.lodgingId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.surveyId')"
              for="rvp-api-lodging-score-surveyId"
            ></label>
            <input
              type="number"
              class="form-control"
              name="surveyId"
              id="rvp-api-lodging-score-surveyId"
              data-cy="surveyId"
              :class="{ valid: !v$.surveyId.$invalid, invalid: v$.surveyId.$invalid }"
              v-model.number="v$.surveyId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.nps')"
              for="rvp-api-lodging-score-nps"
            ></label>
            <input
              type="number"
              class="form-control"
              name="nps"
              id="rvp-api-lodging-score-nps"
              data-cy="nps"
              :class="{ valid: !v$.nps.$invalid, invalid: v$.nps.$invalid }"
              v-model.number="v$.nps.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.rating')"
              for="rvp-api-lodging-score-rating"
            ></label>
            <input
              type="number"
              class="form-control"
              name="rating"
              id="rvp-api-lodging-score-rating"
              data-cy="rating"
              :class="{ valid: !v$.rating.$invalid, invalid: v$.rating.$invalid }"
              v-model.number="v$.rating.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.customScore')"
              for="rvp-api-lodging-score-customScore"
            ></label>
            <input
              type="number"
              class="form-control"
              name="customScore"
              id="rvp-api-lodging-score-customScore"
              data-cy="customScore"
              :class="{ valid: !v$.customScore.$invalid, invalid: v$.customScore.$invalid }"
              v-model.number="v$.customScore.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.fd')"
              for="rvp-api-lodging-score-fd"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="rvp-api-lodging-score-fd"
                  v-model="v$.fd.$model"
                  name="fd"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="rvp-api-lodging-score-fd"
                data-cy="fd"
                type="text"
                class="form-control"
                name="fd"
                :class="{ valid: !v$.fd.$invalid, invalid: v$.fd.$invalid }"
                v-model="v$.fd.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.td')"
              for="rvp-api-lodging-score-td"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="rvp-api-lodging-score-td"
                  v-model="v$.td.$model"
                  name="td"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="rvp-api-lodging-score-td"
                data-cy="td"
                type="text"
                class="form-control"
                name="td"
                :class="{ valid: !v$.td.$invalid, invalid: v$.td.$invalid }"
                v-model="v$.td.$model"
              />
            </b-input-group>
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
<script lang="ts" src="./rvp-api-lodging-score-update.component.ts"></script>
