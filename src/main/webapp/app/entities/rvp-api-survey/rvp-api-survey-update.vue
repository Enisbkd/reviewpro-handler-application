<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="reviewproHandlerApplicationApp.rvpApiSurvey.home.createOrEditLabel"
          data-cy="RvpApiSurveyCreateUpdateHeading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="rvpApiSurvey.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="rvpApiSurvey.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.overallScoreEnabled')"
              for="rvp-api-survey-overallScoreEnabled"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="overallScoreEnabled"
              id="rvp-api-survey-overallScoreEnabled"
              data-cy="overallScoreEnabled"
              :class="{ valid: !v$.overallScoreEnabled.$invalid, invalid: v$.overallScoreEnabled.$invalid }"
              v-model="v$.overallScoreEnabled.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.languages')"
              for="rvp-api-survey-languages"
            ></label>
            <input
              type="text"
              class="form-control"
              name="languages"
              id="rvp-api-survey-languages"
              data-cy="languages"
              :class="{ valid: !v$.languages.$invalid, invalid: v$.languages.$invalid }"
              v-model="v$.languages.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.outOf')"
              for="rvp-api-survey-outOf"
            ></label>
            <input
              type="number"
              class="form-control"
              name="outOf"
              id="rvp-api-survey-outOf"
              data-cy="outOf"
              :class="{ valid: !v$.outOf.$invalid, invalid: v$.outOf.$invalid }"
              v-model.number="v$.outOf.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.name')"
              for="rvp-api-survey-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="rvp-api-survey-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.actibe')"
              for="rvp-api-survey-actibe"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="actibe"
              id="rvp-api-survey-actibe"
              data-cy="actibe"
              :class="{ valid: !v$.actibe.$invalid, invalid: v$.actibe.$invalid }"
              v-model="v$.actibe.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.pids')"
              for="rvp-api-survey-pids"
            ></label>
            <input
              type="text"
              class="form-control"
              name="pids"
              id="rvp-api-survey-pids"
              data-cy="pids"
              :class="{ valid: !v$.pids.$invalid, invalid: v$.pids.$invalid }"
              v-model="v$.pids.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApiSurvey.primary')"
              for="rvp-api-survey-primary"
            ></label>
            <input
              type="checkbox"
              class="form-check"
              name="primary"
              id="rvp-api-survey-primary"
              data-cy="primary"
              :class="{ valid: !v$.primary.$invalid, invalid: v$.primary.$invalid }"
              v-model="v$.primary.$model"
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
<script lang="ts" src="./rvp-api-survey-update.component.ts"></script>
