<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="reviewproHandlerApplicationApp.rvpApilodging.home.createOrEditLabel"
          data-cy="RvpApilodgingCreateUpdateHeading"
          v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="rvpApilodging.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="rvpApilodging.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.ida')"
              for="rvp-apilodging-ida"
            ></label>
            <input
              type="number"
              class="form-control"
              name="ida"
              id="rvp-apilodging-ida"
              data-cy="ida"
              :class="{ valid: !v$.ida.$invalid, invalid: v$.ida.$invalid }"
              v-model.number="v$.ida.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.name')"
              for="rvp-apilodging-name"
            ></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="rvp-apilodging-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
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
<script lang="ts" src="./rvp-apilodging-update.component.ts"></script>
