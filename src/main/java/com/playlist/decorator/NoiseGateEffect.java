package com.playlist.decorator;

/**
 * Efeito que zera amostras cujo valor absoluto fica abaixo de um limiar.
 */
public final class NoiseGateEffect extends AudioEffect {

  private final double threshold;

  /**
   * Cria o efeito de noise gate.
   *
   * @param wrapped áudio decorado.
   * @param threshold limiar de corte.
   */
  public NoiseGateEffect(AudioTrack wrapped, double threshold) {
    super(wrapped);
    if (threshold < 0.0) {
      throw new IllegalArgumentException("Threshold não pode ser menor que zero");
    }
    this.threshold = threshold;
  }

  @Override
  protected String describe() {
    return String.format(java.util.Locale.US, "noiseGate(%.2f)", threshold);
  }

  @Override
  public double[] getSamples() {
    double[] samples = wrapped.getSamples();
    for (int i = 0; i < samples.length; i++) {
      if (Math.abs(samples[i]) < threshold) {
        samples[i] = 0.0;
      }
    }
    return samples;
  }
}