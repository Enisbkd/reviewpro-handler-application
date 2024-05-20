<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rvpApilodging">
        <h2 class="jh-entity-heading" data-cy="rvpApilodgingDetailsHeading">
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.detail.title')"></span> {{ rvpApilodging.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.ida')"></span>
          </dt>
          <dd>
            <span>{{ rvpApilodging.ida }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApilodging.name')"></span>
          </dt>
          <dd>
            <span>{{ rvpApilodging.name }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="rvpApilodging.id"
          :to="{ name: 'RvpApilodgingEdit', params: { rvpApilodgingId: rvpApilodging.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./rvp-apilodging-details.component.ts"></script>
