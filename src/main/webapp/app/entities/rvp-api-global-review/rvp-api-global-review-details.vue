<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="rvpApiGlobalReview">
        <h2 class="jh-entity-heading" data-cy="rvpApiGlobalReviewDetailsHeading">
          <span v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.detail.title')"></span> {{ rvpApiGlobalReview.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.lodgingid')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiGlobalReview.lodgingid }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.prevGri')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiGlobalReview.prevGri }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.distribution')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiGlobalReview.distribution }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.gri')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiGlobalReview.gri }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.fd')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiGlobalReview.fd }}</span>
          </dd>
          <dt>
            <span v-text="t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.td')"></span>
          </dt>
          <dd>
            <span>{{ rvpApiGlobalReview.td }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="rvpApiGlobalReview.id"
          :to="{ name: 'RvpApiGlobalReviewEdit', params: { rvpApiGlobalReviewId: rvpApiGlobalReview.id } }"
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

<script lang="ts" src="./rvp-api-global-review-details.component.ts"></script>
