package com.playlist.decorator;

/**
 * Efeito que aplica uma rampa linear de volume nas primeiras amostras.
 */
public final class FadeInEffect extends AudioEffect {

  private final int sampleCount;

  /**
   * Cria o efeito de fade in.
   *
   * @param wrapped áudio decorado.
   * @param sampleCount quantidade de amostras usadas na rampa.
   */
  public FadeInEffect(AudioTrack wrapped, int sampleCount) {
    super(wrapped);
    if (sampleCount < 0) {
      throw new IllegalArgumentException("sampleCount não pode ser negativo");
    }
    this.sampleCount = sampleCount;
  }

  @Override
  protected String describe() {
    return "fadeIn(" + sampleCount + ")";
  }

  @Override
  public double[] getSamples() {
    double[] samples = wrapped.getSamples();
    int limite = Math.min(sampleCount, samples.length);
    for (int i = 0; i < limite; i++) {
      samples[i] *= ((double) i / sampleCount);
    }
    return samples;
  }
}