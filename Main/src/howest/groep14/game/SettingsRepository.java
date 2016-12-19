package howest.groep14.game;

public class SettingsRepository {
    private static float actorScale = 0.5f;

    public static float getActorScale() {
        return actorScale;
    }

    public static void setActorScale(float actorScale) {
        SettingsRepository.actorScale = actorScale;
    }
}
