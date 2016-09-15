Runx2Dlx5Inhibitory_and = { 'BMP2 = <>', 'TGFb1 = <>', 'Dlx5 = Msx2 && ~Runx2 && BMP2', 'Msx2 = BMP2 && TGFb1 && ~Dlx5','Runx2 = Dlx5 && ~Msx2 && ~TGFb1' }
model_and = ExpressionsToOdefy(Runx2Dlx5Inhibitory_and)
simstruct_and = CreateSimstruct(model_and)
simstruct_and.type = 'hillcube'
simstruct_and = SetInitialValue(simstruct_and, 'Dlx5', 0)
simstruct_and = SetInitialValue(simstruct_and, 'Msx2', 0)
simstruct_and = SetInitialValue(simstruct_and, 'Runx2', 0)
simstruct_and = SetInitialValue(simstruct_and, 'BMP2', 1)
simstruct_and = SetInitialValue(simstruct_and, 'TGFb1', 0)
[t,y] = OdefySimulation(simstruct_and, 0, 0);
plot(t,y);
legend(simstruct_and.model.species);
xlabel(’time’);
ylabel(’expression’);
title("Runx2->Dlx5 inhibitory, BMP2 only, conjunction");