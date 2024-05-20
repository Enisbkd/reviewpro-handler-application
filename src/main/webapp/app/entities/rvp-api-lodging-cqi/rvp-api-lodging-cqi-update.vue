<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="reviewproHandlerApplicationApp.rvpApiLodgingCqi.home.createOrEditLabel"
          data-cy="RvpApiLodgingCqiCreateUpdateHeading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="rvpApiLodgingCqi.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="rvpApiLodgingCqi.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.lodgingId')"
              for="rvp-api-lodging-cqi-lodgingId"
            ></label>
            <input
              type="number"
              class="form-control"
              name="lodgingId"
              id="rvp-api-lodging-cqi-lodgingId"
              data-cy="lodgingId"
              :class="{ valid: !v$.lodgingId.$invalid, invalid: v$.lodgingId.$invalid }"
              v-model.number="v$.lodgingId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.averageCurrentPeriod')"
              for="rvp-api-lodging-cqi-averageCurrentPeriod"
            ></label>
            <input
              type="text"
              class="form-control"
              name="averageCurrentPeriod"
              id="rvp-api-lodging-cqi-averageCurrentPeriod"
              data-cy="averageCurrentPeriod"
              :class="{ valid: !v$.averageCurrentPeriod.$invalid, invalid: v$.averageCurrentPeriod.$invalid }"
              v-model="v$.averageCurrentPeriod.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.tendancy')"
              for="rvp-api-lodging-cqi-tendancy"
            ></label>
            <input
              type="text"
              class="form-control"
              name="tendancy"
              id="rvp-api-lodging-cqi-tendancy"
              data-cy="tendancy"
              :class="{ valid: !v$.tendancy.$invalid, invalid: v$.tendancy.$invalid }"
              v-model="v$.tendancy.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.change')"
              for="rvp-api-lodging-cqi-change"
            ></label>
            <input
              type="text"
              class="form-control"
              name="change"
              id="rvp-api-lodging-cqi-change"
              data-cy="change"
              :class="{ valid: !v$.change.$invalid, invalid: v$.change.$invalid }"
              v-model="v$.change.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.name')"
              for="rvp-api-lodging-cqi-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="rvp-api-lodging-cqi-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.averagePreviousPeriod')"
              for="rvp-api-lodging-cqi-averagePreviousPeriod"
            ></label>
            <input
              type="text"
              class="form-control"
              name="averagePreviousPeriod"
              id="rvp-api-lodging-cqi-averagePreviousPeriod"
              data-cy="averagePreviousPeriod"
              :class="{ valid: !v$.averagePreviousPeriod.$invalid, invalid: v$.averagePreviousPeriod.$invalid }"
              v-model="v$.averagePreviousPeriod.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.fd')"
              for="rvp-api-lodging-cqi-fd"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="rvp-api-lodging-cqi-fd"
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
                id="rvp-api-lodging-cqi-fd"
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
              v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.td')"
              for="rvp-api-lodging-cqi-td"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="rvp-api-lodging-cqi-td"
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
                id="rvp-api-lodging-cqi-td"
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
<script lang="ts" src="./rvp-api-lodging-cqi-update.component.ts"></script>
