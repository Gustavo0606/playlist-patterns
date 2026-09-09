package com.playlist.adapter;

import com.playlist.adapter.external.LegacyVinylCatalog;
import com.playlist.core.Track;

import javax.sound.sampled.Line;
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
    throw new UnsupportedOperationException("Exercício 2: implemente VinylCatalogAdapter.findAll");
  }

  @Override
  public Optional<Track> findById(String id) {
    if (id == null || id.isEmpty()) {
    return Optional.empty();
    }

  }

  private Optional<Track> converterLinhaParaTrack(String linha) {
      if (linha == null || linha.isEmpty()) {
          return Optional.empty();
      }
      String[] partes = linha.split("|");
      if (partes.length != 5){
          return Optional.empty();
      }
      try{
          String id = partes[0];
          if (id == null || id.isEmpty()) {return Optional.empty();}
          String title = partes[1];
          String artist = partes[2];
          int durationSeconds = Integer.parseInt(partes[3].trim())/1000;
          if (durationSeconds < 0) {return Optional.empty();}

          Boolean premium = partes[4].trim().equalsIgnoreCase("Y");


      }catch (Exception e){
          return Optional.empty();
      }
  }

  private String Formatar(List<String> nomes) {
      if (nomes == null || nomes.isEmpty()) {
          return "";
      }

      StringBuilder nomeCompleto = new StringBuilder();
      for (String nome : nomes) {
          if (nome != null && !nome.isBlank()){
              if (!nomeCompleto.isEmpty()) {
                  nomeCompleto.append(" ");
              }
              String formatado = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
              nomeCompleto.append(formatado);      }
      }
      return nomeCompleto.toString();
  }
}
