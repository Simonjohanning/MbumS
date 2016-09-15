function [models, sims] = createExpressions
#for index = [0 : 242]
#  models = [0:242]
#  sims = [0:242]
  models = [0:0]
  sims = [0:2]
for index = [0 : 2]

# behavior of Msx2
Msx2String = "Msx2 = TGFb1 || BMP2";
if(rem(floor(index / 3), 3) - 1 == -1)
	Msx2String = [Msx2String, " || ~Dlx5"];
elseif(rem(floor(index / 3), 3) - 1 == 1)
	Msx2String = [Max2String, " || Dlx5"];
endif

# behavior of Dlx5
Dlx5String = "Dlx5 = BMP2"
# 	influence of Msx2
if(rem(floor(index), 3) - 1 == -1)
	Dlx5String = [Dlx5String, " || ~Msx2"]
elseif(rem(floor(index), 3) - 1 == 1)
	Dlx5String = [Dlx5String, " || Msx2"]
endif
#	influence of Runx2  
if(rem(floor(index / 81), 3) - 1 == -1)
	Dlx5String = [Dlx5String, " || ~Runx2"]
elseif(rem(floor(index / 81), 3) - 1 == 1)
	Dlx5String = [Dlx5String, " || Runx2"]
endif

# behavior of Runx2
Runx2String = "Runx2 = Dlx5"
# 	influence of Msx2
if(rem(floor(index / 9), 3) - 1 == -1)
	Runx2String = [Runx2String " || ~Msx2"]
elseif(rem(floor(index / 9), 3) - 1 == 1)
	Runx2String = [Runx2String " || Msx2"]
endif
#	influence of TGFb1  
if(rem(floor(index / 27), 3) - 1 == -1)
	Runx2String = [Runx2String " || ~TGFb1"]
elseif(rem(floor(index / 27), 3) - 1 == 1)
	Runx2String = [Runx2String " || TGFb1"]
endif

# construct model string
	  expressionString = ["BMP2 = <>,", " TGFb1 = <>, ",Msx2String, ", ", Dlx5String, ", ", Runx2String]
# construct BM for odefy
expression = {"BMP2 = <>", " TGFb1 = <>",Msx2String, Dlx5String, Runx2String};
model = ExpressionsToOdefy(expression);
#states = BooleanStates(model);
#PrettyPrintStates(model, states);
#models(index) = model
# construct struct for simulation
simstruct = CreateSimstruct(model);
#simstruct.type = 'boolrandom';
simstruct.type = 'hillcubenorm';
simstruct.timeto = 10;
# set presence of BMP2
simstruct = SetInitialValue(simstruct,'BMP2',1);
# set presence of TGFb1
simstruct = SetInitialValue(simstruct,'TGFb1',0);
# simulate 
#simres = OdefySimulation(simstruct);
[t y] = OdefySimulation(simstruct,0,0);
plot(t,y);
#plot(simstruct);
legend(model.species);
xlabel('time');
ylabel('expression')

endfor
endfunction

