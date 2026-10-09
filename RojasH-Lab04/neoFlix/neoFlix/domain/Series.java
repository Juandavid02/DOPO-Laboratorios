package domain;  
 
import java.util.ArrayList;

public class Series extends Content{
   
    private int year;
    private ArrayList<Episode> episodes;
    

    public Series(String title, int year){
        super(title);
        this.year=year;
        episodes= new ArrayList<Episode>();
    }


  
    public void addEpisode(Episode e){
        episodes.add(e);
    }


    /**
     * Calcula el rating de la serie como el promedio del rating de sus episodios.
     * Si algo falla, lanza la excepción.
     *
     * @return el promedio de los ratings de los episodios
     * @throws NeoFlixException CONTENT_EMPTY si la serie no tiene episodios,
     *         VALUE_UNKNOWN si algún episodio no tiene rating,
     *         DATA_ERROR si algún episodio tiene datos errados
     */
   @Override
   public int rating() throws NeoFlixException{
       int acum = 0;
       if (episodes.isEmpty()) throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);
       for (Episode e : episodes){
           acum += e.rating();
       }
       return acum/episodes.size();
   }

    /**
     * Calcula el rating de la serie. Si un episodio no tiene rating usa el
     * valor por defecto, y si tiene datos errados lo ignora.
     *
     * @param default_ valor que se usa para los episodios sin rating
     * @return el promedio de los ratings (los episodios ignorados no cuentan)
     * @throws NeoFlixException CONTENT_EMPTY si la serie no tiene episodios,
     *         VALUE_UNKNOWN si todos los episodios fueron ignorados
     */
   public int rating(int default_) throws NeoFlixException{
       int acum_episodios = 0;
       int acum = 0;
       if (episodes.isEmpty()) throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);
       for (Episode episode : episodes){
           try {
               acum += episode.rating();
               acum_episodios += 1;
           } catch (NeoFlixException e){
               if (e.getMessage().equals(NeoFlixException.VALUE_UNKNOWN)){
                   acum += default_;
                   acum_episodios += 1;
               } else if (e.getMessage().equals(NeoFlixException.DATA_ERROR)){
                   // Se ignora
               }
           }
       }
       if (acum_episodios == 0){
           throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
       }
       return acum/acum_episodios;
   }

    /**
     * Calcula el rating de la serie rellenando los episodios que no tienen rating
     * (los de datos errados se tratan igual que los desconocidos).
     * Si previous es true, el reemplazo es el promedio de los episodios anteriores,
     * contando los que ya fueron reemplazados. Si es false, es el promedio de
     * los episodios que sí tienen rating.
     *
     * @param previous true para usar los anteriores, false para usar todos
     * @return el promedio de los ratings, ya con los reemplazos
     * @throws NeoFlixException CONTENT_EMPTY si la serie no tiene episodios,
     *         VALUE_UNKNOWN si no hay de dónde sacar un reemplazo
     */
   public int rating(boolean previous) throws NeoFlixException{
       if (episodes.isEmpty()) throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);
       int acum = 0;
       int acum_final = 0;
       if (!previous) {
           int acum_rating_real = 0;
           for (Episode episode : episodes) {
               try {
                   acum += episode.rating();
                   acum_rating_real += 1;
               } catch (NeoFlixException e) {
                   //Ignora independiente de cual sea
               }
           }
           if (acum_rating_real == 0) throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
           int cambio = acum/acum_rating_real;
           for (Episode episode : episodes) {
               try {
                   acum_final += episode.rating();
               } catch (NeoFlixException e) {
                   acum_final += cambio;
               }
           }
       } else {
           int acum_episodios = 0;
           for (Episode episode : episodes) {
               try {
                   acum_final += episode.rating();
                   acum_episodios += 1;
               } catch (NeoFlixException e) {
                   if (acum_episodios == 0){
                       throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
                   }
                   acum_final += acum_final/ acum_episodios;
                   acum_episodios +=1;
               }
           }
       }
       return acum_final/episodes.size();
   }
    /**
     * Calcula la popularidad de la serie como el promedio de la popularidad
     * de sus episodios. Los episodios que nadie ha empezado a ver se ignoran.
     *
     * @return el promedio de la popularidad de los episodios que sí se pudieron calcular
     * @throws NeoFlixException CONTENT_EMPTY si la serie no tiene episodios,
     *         VALUE_UNKNOWN si ningún episodio tiene intentos,
     *         DATA_ERROR si algún episodio tiene más terminados que intentos
     */
   @Override
   public int popularity() throws NeoFlixException{
       if (episodes.isEmpty()) throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);
       int acum = 0;
       int acum_episodios = 0;
       for (Episode episode : episodes) {
           try {
               acum += episode.popularity();
               acum_episodios+=1;
           } catch (NeoFlixException e){
               if (!e.getMessage().equals(NeoFlixException.VALUE_UNKNOWN)){
                   throw e;
               }
           }
       }
       if (acum_episodios == 0) throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
       return acum/acum_episodios;
   }
    
    
    @Override
    public String data(boolean withIndicators) throws NeoFlixException{
        StringBuffer answer=new StringBuffer();
        answer.append(title+": "+year+ ( withIndicators ? " ( " +popularity()+" - "+ rating()+" )":""));
        for(Episode e: episodes) {
            answer.append("\n\t"+(e.data(withIndicators)));
        }
        return answer.toString();
    } 
    

}
