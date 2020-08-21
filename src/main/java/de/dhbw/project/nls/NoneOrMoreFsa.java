package de.dhbw.project.nls;

public class NoneOrMoreFsa {

    private IBuildFsa nextFsaBuilder;
    private IBuildFsa moreFsaBuilder;

    public NoneOrMoreFsa(CompositeFsa parent, IBuildFsa moreFsaBuilder, IBuildFsa nextFsaBuilder) {
        this.moreFsaBuilder = moreFsaBuilder;
        this.nextFsaBuilder = nextFsaBuilder;
        createLazyOr(parent);
    }

    private LazyCompositeFsa createLazyOr(CompositeFsa parent) {
        return new LazyCompositeFsa(p -> {
            OrCompositeFsa or = OrCompositeFsa.create((IConsumeIndex) p);
            nextFsaBuilder.build(or);
            AndCompositeFsa and = new AndCompositeFsa(or);
            moreFsaBuilder.build(and);
            createLazyOr(and);

            return or;
        }, parent);
    }
}
