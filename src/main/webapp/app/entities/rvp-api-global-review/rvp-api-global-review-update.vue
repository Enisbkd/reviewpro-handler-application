<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="reviewproHandlerApplicationApp.rvpApiGlobalReview.home.createOrEditLabel"
          data-cy="RvpApiGlobalReviewCreateUpdateHeading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="rvpApiGlobalReview.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="rvpApiGlobalReview.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.lodgingid')"
              for="rvp-api-global-review-lodgingid"
            ></label>
            <input
              type="number"
              class="form-control"
              name="lodgingid"
              id="rvp-api-global-review-lodgingid"
              data-cy="lodgingid"
              :class="{ valid: !v$.lodgingid.$invalid, invalid: v$.lodgingid.$invalid }"
              v-model.number="v$.lodgingid.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.prevGri')"
              for="rvp-api-global-review-prevGri"
            ></label>
            <input
              type="number"
              class="form-control"
              name="prevGri"
              id="rvp-api-global-review-prevGri"
              data-cy="prevGri"
              :class="{ valid: !v$.prevGri.$invalid, invalid: v$.prevGri.$invalid }"
              v-model.number="v$.prevGri.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.distribution')"
              for="rvp-api-global-review-distribution"
            ></label>
            <input
              type="text"
              class="form-control"
              name="distribution"
              id="rvp-api-global-review-distribution"
              data-cy="distribution"
              :class="{ valid: !v$.distribution.$invalid, invalid: v$.distribution.$invalid }"
              v-model="v$.distribution.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.gri')"
              for="rvp-api-global-review-gri"
            ></label>
            <input
              type="number"
              class="form-control"
              name="gri"
              id="rvp-api-global-review-gri"
              data-cy="gri"
              :class="{ valid: !v$.gri.$invalid, invalid: v$.gri.$invalid }"
              v-model.number="v$.gri.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.fd')"
              for="rvp-api-global-review-fd"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="rvp-api-global-review-fd"
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
                id="rvp-api-global-review-fd"
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
              v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.td')"
              for="rvp-api-global-review-td"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="rvp-api-global-review-td"
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
                id="rvp-api-global-review-td"
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
<script lang="ts" src="./rvp-api-global-review-update.component.ts"></script>
