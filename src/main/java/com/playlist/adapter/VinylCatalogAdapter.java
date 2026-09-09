package com.playlist.adapter;

import com.playlist.adapter.external.LegacyVinylCatalog;
import com.playlist.core.Track;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Adapter que converte os registros do {@link LegacyVinylCatalog} para {@link Track}.
 */
public class VinylCatalogAdapter implements TrackCatalog {

  private final LegacyVinylCatalog legacyCatalog;

  /**
   * Cria o adapter em cima do sistema legado.
   *
   * @param legacyCatalog catálogo legado a ser adaptado. Não pode ser nulo.
   * @throws IllegalArgumentException se o catálogo for nulo.
   */
  public VinylCatalogAdapter(LegacyVinylCatalog legacyCatalog) {
    if (legacyCatalog == null) {
      throw new IllegalArgumentException("Sistema legado é ogrigatório");
    }
    this.legacyCatalog = legacyCatalog;
  }

  @Override
  public List<Track> findAll() {
    List<Track> tracks = new ArrayList<>();
    String[] linhasLegado = legacyCatalog.fetchAllRecords();
    for (String linha : linhasLegado) {
      converterLinhaParaTrack(linha).ifPresent(tracks::add);
    }
    return tracks;
  }

  @Override
  public Optional<Track> findById(String id) {
    if (id == null || id.isBlank()) {
      return Optional.empty();
    }
    String linhaBruta = legacyCatalog.findRecordByCatalogNumber(id);
    return converterLinhaParaTrack(linhaBruta);
  }

  private Optional<Track> converterLinhaParaTrack(String linha) {
    if (linha == null || linha.isEmpty()) {
      return Optional.empty();
    }
    String[] partes = linha.split("\\|");
    if (partes.length != 5) {
      return Optional.empty();
    }
    try {
      String id = partes[0].trim();
      if (id.isEmpty()) {
        return Optional.empty();
      }
      String title = formatarTitulo(partes[1]);
      if (title.isBlank()) {
        return Optional.empty();
      }
      String artist = formatarArtista(partes[2]);
      int duracaoMs = Integer.parseInt(partes[3].trim());
      if (duracaoMs < 0) {
        return Optional.empty();
      }
      int durationSeconds = duracaoMs / 1000;

      boolean premium = partes[4].trim().equalsIgnoreCase("Y");

      return Optional.of(new Track(id, title, artist, durationSeconds, premium));
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  private String capitalizarPalavra(String palavra) {
    if (palavra == null || palavra.isBlank()) {
      return "";
    }
    String limpa = palavra.trim();
    return limpa.substring(0, 1).toUpperCase() + limpa.substring(1).toLowerCase();
  }

  private String formatarTitulo(String titulo) {
    if (titulo == null || titulo.isBlank()) {
      return "";
    }
    String[] palavras = titulo.split("\\s+");
    StringBuilder resultado = new StringBuilder();
    for (String p : palavras) {
      if (!resultado.isEmpty()) {
        resultado.append(" ");
      }
      resultado.append(capitalizarPalavra(p));
    }
    return resultado.toString();
  }

  private String formatarArtista(String artistaBruto) {
    if (artistaBruto == null || !artistaBruto.contains(",")) {
      return "";
    }

    String[] partes = artistaBruto.split(",");
    if (partes.length < 2) {
      return "";
    }

    String sobrenome = formatarTitulo(partes[0]);
    String nome = formatarTitulo(partes[1]);

    return nome + " " + sobrenome;
  }
}