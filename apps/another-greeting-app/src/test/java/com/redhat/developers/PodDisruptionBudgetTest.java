package com.redhat.developers;

import io.fabric8.kubernetes.api.model.policy.v1.PodDisruptionBudget;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.server.mock.KubernetesServer;
import io.quarkus.test.junit.DisabledOnNativeImage;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kubernetes.client.KubernetesTestServer;
import io.quarkus.test.kubernetes.client.WithKubernetesTestServer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@WithKubernetesTestServer
@QuarkusTest
@DisabledOnNativeImage
public class PodDisruptionBudgetTest {

  @KubernetesTestServer
  KubernetesServer mocKubernetesServer;

  @Test
  public void testPdbFromFile() {
    String path = "./src/main/kubernetes/pdb.yml";
    PodDisruptionBudget pdb = mocKubernetesServer.getClient().policy().v1().podDisruptionBudget().load(path).get();
    assertThrows(KubernetesClientException.class, () -> mocKubernetesServer.getClient().policy().v1().podDisruptionBudget().create(pdb));
  }

}