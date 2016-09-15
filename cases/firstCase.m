simstruct_and = SetInitialValue(simstruct_and, 'BMP2', 1)
simstruct_and = SetInitialValue(simstruct_and, 'TGFb1', 0)
[t,y] = OdefySimulation(simstruct_and, 0, 0);
plot(t,y, {".", ".", 'd', '*', 'p'}, "markersize", 2);
legend(simstruct_and.model.species);
xlabel("time");
ylabel("expression");
title("Runx2->Dlx5 inhibitory, BMP2 only, conjunction");
axis([0, 10, -0.1, 1.1]);