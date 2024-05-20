<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rvpApiLodgingScore">
        <h2 class="jh-entity-heading" data-cy="rvpApiLodgingScoreDetailsHeading">
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.detail.title')"></span> {{ rvpApiLodgingScore.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.lodgingId')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingScore.lodgingId }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.surveyId')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingScore.surveyId }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.nps')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingScore.nps }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.rating')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingScore.rating }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.customScore')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingScore.customScore }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.fd')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingScore.fd }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.td')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiLodgingScore.td }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="rvpApiLodgingScore.id"
          :to="{ name: 'RvpApiLodgingScoreEdit', params: { rvpApiLodgingScoreId: rvpApiLodgingScore.id } }"
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

<script lang="ts" src="./rvp-api-lodging-score-details.component.ts"></script>
