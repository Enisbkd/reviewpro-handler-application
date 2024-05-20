<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rvpApiLodgingCqi">
        <h2 class="jh-entity-heading" data-cy="rvpApiLodgingCqiDetailsHeading">
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.detail.title')"></span> {{ rvpApiLodgingCqi.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.lodgingId')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingCqi.lodgingId }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.averageCurrentPeriod')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingCqi.averageCurrentPeriod }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.tendancy')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingCqi.tendancy }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.change')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingCqi.change }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.name')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingCqi.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.averagePreviousPeriod')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingCqi.averagePreviousPeriod }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.fd')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingCqi.fd }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.td')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingCqi.td }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="rvpApiLodgingCqi.id"
          :to="{ name: 'RvpApiLodgingCqiEdit', params: { rvpApiLodgingCqiId: rvpApiLodgingCqi.id } }"
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

<script lang="ts" src="./rvp-api-lodging-cqi-details.component.ts"></script>
