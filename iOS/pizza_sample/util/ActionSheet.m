//
// ActionSheet.m
//

#import "ActionSheet.h"

@interface ActionSheet ()

@property (strong, nonatomic) void (^completion)(NSInteger);

@end

@implementation ActionSheet

// ActionSheet初期化
- (id)initWithTitle:(NSString *)title completion:(void (^)(NSInteger buttonIndex))completion cancelButtonTitle:(NSString *)cancelButtonTitle destructiveButtonTitle:(NSString *)destructiveButtonTitle otherButtonTitles:(NSString *)otherButtonTitles, ...
{
	self = [super initWithTitle:title delegate:self cancelButtonTitle:cancelButtonTitle destructiveButtonTitle:destructiveButtonTitle otherButtonTitles:nil];
	
	if (self != nil) {
		_completion = completion;
		
		va_list _arguments;
		va_start(_arguments, otherButtonTitles);
		
		for (NSString *key = otherButtonTitles; key != nil; key = (__bridge NSString *)va_arg(_arguments, void *)) {
			[self addButtonWithTitle:key];
		}
		
		va_end(_arguments);
	}
	
	return self;
}

#pragma mark - UIActionSheet delegate method

// ボタンがクリックされた時に呼ばれるメソッド
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
	if (_completion != nil) {
		_completion(buttonIndex);
	}
}

@end
