<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rvpapilodging">
        <h2 class="jh-entity-heading" data-cy="rvpapilodgingDetailsHeading">
          <span v-text="t$('reviewproHandlerApplicationApp.rvpapilodging.detail.title')"></span> {{ rvpapilodging.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpapilodging.ida')"></span>
          </dt>
          <dd>
            <span>{{ rvpapilodging.ida }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpapilodging.name')"></span>
          </dt>
          <dd>
            <span>{{ rvpapilodging.name }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="rvpapilodging.id"
          :to="{ name: 'RvpapilodgingEdit', params: { rvpapilodgingId: rvpapilodging.id } }"
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

<script lang="ts" src="./rvpapilodging-details.component.ts"></script>
